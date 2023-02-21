package com.example.movieapptest.ui.moviefavourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.movieapptest.data.repository.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.movieapptest.data.model.Result
import com.example.movieapptest.data.room.entity.FavouriteEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by sathish on 21,February,2023
 */

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val favouriteRepository: FavouriteRepository) :
    ViewModel() {

     var favoriteResult = MutableStateFlow<List<FavouriteEntity>>(emptyList())

    init {
        getAllFavourite()
    }

    fun getAllFavourite() {

        viewModelScope.launch {

            favouriteRepository.getAllFavourite().collect {

                favoriteResult.emit(it)

            }
        }
    }

}