package com.example.movieapptest.data.network

import com.example.movieapptest.data.model.Genre
import com.example.movieapptest.data.model.MovieDetailModel
import com.example.movieapptest.data.model.MoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by sathish on 18,February,2023
 */
interface ApiInterface {


    @GET(ApiConfig.NOW_PLAYING)
    suspend fun nowPlaying(@Query("page")page:Int,@Query("api_key") apikey:String):Response<MoviesModel>

    @GET(ApiConfig.POPULAR)
    suspend fun popular(@Query("page")page:Int,@Query("api_key") apikey:String):Response<MoviesModel>

    @GET(ApiConfig.TOP_RATED)
    suspend fun topRated(@Query("page")page:Int,@Query("api_key") apikey:String):Response<MoviesModel>

    @GET(ApiConfig.UPCOMING)
    suspend fun upcoming(@Query("page")page:Int,@Query("api_key") apikey:String):Response<MoviesModel>

    @GET
    suspend fun genre(@Url url:String):Response<Genre>

    @GET
    suspend fun movieDetail(@Url url:String):Response<MovieDetailModel>

    @GET(ApiConfig.SEARCH)
    suspend fun search(@Query("page")page:Int,@Query("api_key") apikey:String):Response<MoviesModel>

}