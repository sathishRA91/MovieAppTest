package com.example.movieapptest.ui.moviedetail

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapptest.base.AppConstant
import com.example.movieapptest.base.ResultResource
import com.example.movieapptest.data.model.Genres
import com.example.movieapptest.data.model.MovieDetailModel
import com.example.movieapptest.data.model.MoviesModel
import com.example.movieapptest.data.repository.MovieRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by sathish on 20,February,2023
 */
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
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
    var isFavourite = MutableStateFlow(false)

    init {
        movieDetail("505642")
    }


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
                    val language = data.spoken_languages.map { it.name }
                    spokenLanguage.value = language.toString()
                    status.value = data.poster_path

                }
        }

    }

}