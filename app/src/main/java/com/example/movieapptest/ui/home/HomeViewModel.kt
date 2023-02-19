package com.example.movieapptest.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapptest.data.model.Result
import com.example.movieapptest.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by sathish on 19,February,2023
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    val nowPlayingMovies = movieRepository.getNowPlaying()
    val popularMovies = movieRepository.getPopular()
    val topRatedMovies = movieRepository.getTopRated()
    val upcomingMovies = movieRepository.getUpcoming()


}