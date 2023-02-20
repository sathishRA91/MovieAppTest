package com.example.movieapptest.data.repository

import androidx.paging.PagingData
import com.example.movieapptest.base.ResultResource
import com.example.movieapptest.data.model.Genre
import com.example.movieapptest.data.model.Genres
import com.example.movieapptest.data.model.MovieDetailModel
import com.example.movieapptest.data.model.Result
import kotlinx.coroutines.flow.Flow

/**
 * Created by sathish on 18,February,2023
 */
interface MovieRepository {

    fun getNowPlaying(): Flow<PagingData<Result>>

    fun getPopular(): Flow<PagingData<Result>>

    fun getTopRated(): Flow<PagingData<Result>>

    fun getUpcoming(): Flow<PagingData<Result>>

    suspend fun getGenre(): Flow<List<Genres>>

    suspend fun getMovieDetail(movieId:String): Flow<MovieDetailModel>

    fun getSearchMovie(searchQuery:String): Flow<PagingData<Result>>

}