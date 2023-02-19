package com.example.movieapptest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapptest.databinding.FragmentUpcomingBinding
import com.example.movieapptest.ui.adapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpcomingFragment :Fragment() {
    private val viewModel: HomeViewModel by activityViewModels()

    private lateinit var upcomingBinding: FragmentUpcomingBinding

    private lateinit var movieAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        upcomingBinding = FragmentUpcomingBinding.inflate(inflater, container, false)

        viewUpcomingMovie()

        return upcomingBinding.root

    }

    private fun viewUpcomingMovie() {

        movieAdapter= MoviesAdapter()
        upcomingBinding.RvUpcoming.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.upcomingMovies.collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }



}