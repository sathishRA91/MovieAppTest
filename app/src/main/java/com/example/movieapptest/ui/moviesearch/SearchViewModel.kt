package com.example.movieapptest.ui.moviesearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapptest.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.movieapptest.data.model.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created by sathish on 20,February,2023
 */
@HiltViewModel
class SearchViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    val searchQuery=MutableStateFlow("")


    val getSearchMovieList = searchQuery.flatMapLatest { searchQuery->
        movieRepository.getSearchMovie(searchQuery)}

    fun search():String {
       return searchQuery.value
    }

}