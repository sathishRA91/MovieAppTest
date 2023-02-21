package com.example.movieapptest.ui.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.movieapptest.base.AppConstant
import com.example.movieapptest.data.repository.MovieRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by sathish on 19,February,2023
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val preference: SharedPreferences
) : ViewModel() {


    init {
        getGenre()
    }

    var title = MutableStateFlow("")
    val nowPlayingMovie = movieRepository.getNowPlaying()
    val popularMovies = movieRepository.getPopular()
    val topRatedMovies = movieRepository.getTopRated()
    val upcomingMovies = movieRepository.getUpcoming()

    private fun getGenre() {
        val genreValue = preference.getString(AppConstant.GENRE_ITEM, null)
        viewModelScope.launch(Dispatchers.IO) {
            if (genreValue == null) {
                movieRepository.getGenre().collectLatest { data ->
                    preference.edit().putString(AppConstant.GENRE_ITEM, Gson().toJson(data)).apply()
                }
            }
        }
    }

}