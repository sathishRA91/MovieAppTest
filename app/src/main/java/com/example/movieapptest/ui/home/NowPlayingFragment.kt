package com.example.movieapptest.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.filter
import androidx.paging.flatMap
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapptest.databinding.FragmentNowPlayingBinding
import com.example.movieapptest.ui.adapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NowPlayingFragment : Fragment() {


    private val viewModel: HomeViewModel by activityViewModels()

    private lateinit var nowPlayingBinding: FragmentNowPlayingBinding

    private lateinit var movieAdapter: MoviesAdapter

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

        movieAdapter=MoviesAdapter()
        nowPlayingBinding.RvNowPlaying.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.nowPlayingMovies.collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }


}