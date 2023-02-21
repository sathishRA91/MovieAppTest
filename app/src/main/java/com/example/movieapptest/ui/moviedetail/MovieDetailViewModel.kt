package com.example.movieapptest.ui.moviedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapptest.base.ResultResource
import com.example.movieapptest.data.model.MovieDetailModel
import com.example.movieapptest.data.repository.FavouriteRepository
import com.example.movieapptest.data.repository.MovieRepository
import com.example.movieapptest.data.room.entity.FavouriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by sathish on 20,February,2023
 */
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val favouriteRepository: FavouriteRepository
) :
    ViewModel() {


    val result = MutableStateFlow<ResultResource<MovieDetailModel>?>(null)

    val resultResponse = result.asStateFlow()

    var backDropImage = MutableStateFlow("")
    var posterImage = MutableStateFlow("")
    var title = MutableStateFlow("")
    var tagline = MutableStateFlow("")
    var overview = MutableStateFlow("")
    var genre = MutableStateFlow("")
    var releaseDate = MutableStateFlow("")
    var voteAverage = MutableStateFlow("")
    var voteCount = MutableStateFlow("")
    var spokenLanguage = MutableStateFlow("")
    var status = MutableStateFlow("")
    var isFavourite = MutableLiveData(false)
    var favouriteEntity: FavouriteEntity? = null


    fun movieDetail(movieId: String) {
        result.value = ResultResource.Loading()

        viewModelScope.launch {
            val response = movieRepository.getMovieDetail(movieId)

            response.catch { e -> result.value = ResultResource.ErrorMessage(e.toString()) }
                .collect { data ->
                    result.value = ResultResource.Success(data)
                    backDropImage.value = data.backdrop_path
                    posterImage.value = data.poster_path
                    title.value = data.title
                    tagline.value = data.tagline
                    overview.value = data.overview
                    val genreContent = data.genres.map { it.name }
                    genre.value = genreContent.toString()
                    releaseDate.value = data.release_date
                    voteAverage.value = data.vote_average.toString()
                    voteCount.value = data.vote_count.toString()
                    val language = data.spoken_languages.map { it.name }.filter { it.isNotEmpty() }
                    spokenLanguage.value = language.toString()
                    status.value = data.status
                    favouriteEntity = FavouriteEntity(
                        0,
                        data.poster_path,
                        data.title,
                        genreContent.toString(),
                        data.release_date,
                        data.vote_average.toString(),
                        data.vote_count.toString(),
                        movieId
                    )
                    val favouriteData = favouriteRepository.checkFavouriteExist(movieId)
                    if (favouriteData != null) {
                        isFavourite.postValue(true)
                    }

                }
        }

    }


    fun addRemoveFavourite(movieId: String) {
        viewModelScope.launch {
            if (isFavourite.value == true) {
                favouriteRepository.deleteFavourite(movieId)
                isFavourite.postValue(false)
            } else {
                isFavourite.postValue(true)
                favouriteRepository.insertFavourite(favouriteEntity!!)
            }
        }
    }
}