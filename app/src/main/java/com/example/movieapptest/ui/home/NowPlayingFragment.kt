package com.example.movieapptest.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.filter
import androidx.paging.flatMap
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapptest.R
import com.example.movieapptest.base.AppConstant
import com.example.movieapptest.data.model.Genres
import com.example.movieapptest.databinding.FragmentNowPlayingBinding
import com.example.movieapptest.ui.adapter.MoviesAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NowPlayingFragment : Fragment() {


    private val viewModel: HomeViewModel by activityViewModels()

    private lateinit var nowPlayingBinding: FragmentNowPlayingBinding

    private lateinit var movieAdapter: MoviesAdapter

    @Inject
    lateinit var preferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        nowPlayingBinding = FragmentNowPlayingBinding.inflate(inflater, container, false)


        viewNowPlayingMovie()

        return nowPlayingBinding.root

    }

    private fun viewNowPlayingMovie() {

        viewModel.title.value = requireContext().getString(R.string.now_playing)
        movieAdapter = MoviesAdapter(requireContext())
        nowPlayingBinding.RvNowPlaying.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.nowPlayingMovie.collectLatest { pagingData ->
                val pagingResult = pagingData.map { result ->
                    val genreCategory = ArrayList<String>()
                    val genreList = preferences.getString(AppConstant.GENRE_ITEM, null)
                    result.genre_ids.forEach { genreId ->
                        if (genreList != null) {
                            val genreData: List<Genres> =
                                Gson().fromJson(
                                    genreList,
                                    object : TypeToken<List<Genres>>() {}.type
                                )

                            for (i in genreData.indices) {
                                if (genreData[i].id == genreId) {
                                    genreCategory.add(genreData[i].name)
                                }
                            }
                        }
                        result.genre_categoryName = genreCategory
                    }
                    result
                }
                movieAdapter.submitData(pagingResult)
            }


        }

        movieAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading)
                nowPlayingBinding.PbBar.isVisible = true
            else {
                nowPlayingBinding.PbBar.isVisible = false
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG).show()
                }

            }
        }
    }


}