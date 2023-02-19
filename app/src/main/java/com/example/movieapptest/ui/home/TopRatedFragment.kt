package com.example.movieapptest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapptest.databinding.FragmentNowPlayingBinding
import com.example.movieapptest.databinding.FragmentTopRatedBinding
import com.example.movieapptest.ui.adapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopRatedFragment :Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()

    private lateinit var topRatedBinding: FragmentTopRatedBinding

    private lateinit var movieAdapter: MoviesAdapter

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

        movieAdapter= MoviesAdapter()
        topRatedBinding.RvTopRated.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.topRatedMovies.collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }



}