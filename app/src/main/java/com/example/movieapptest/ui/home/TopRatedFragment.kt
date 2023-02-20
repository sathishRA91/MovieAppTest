package com.example.movieapptest.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapptest.R
import com.example.movieapptest.base.AppConstant
import com.example.movieapptest.data.model.Genres
import com.example.movieapptest.databinding.FragmentNowPlayingBinding
import com.example.movieapptest.databinding.FragmentTopRatedBinding
import com.example.movieapptest.ui.adapter.MoviesAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TopRatedFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()

    private lateinit var topRatedBinding: FragmentTopRatedBinding

    private lateinit var movieAdapter: MoviesAdapter

    @Inject
    lateinit var preferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        topRatedBinding = FragmentTopRatedBinding.inflate(inflater, container, false)

        viewTopRatedMovie()

        return topRatedBinding.root

    }

    private fun viewTopRatedMovie() {
        viewModel.title.value=requireContext().getString(R.string.top_rated)
        movieAdapter = MoviesAdapter(requireContext())
        topRatedBinding.RvTopRated.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val genreList = preferences.getString(AppConstant.GENRE_ITEM, null)
            viewModel.topRatedMovies.collectLatest { pagingData ->

                val pagingResult = pagingData.map { result ->
                    val genreCategory = ArrayList<String>()
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
    }


}