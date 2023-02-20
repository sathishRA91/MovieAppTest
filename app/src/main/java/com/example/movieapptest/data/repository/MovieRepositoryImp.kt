package com.example.movieapptest.data.repository

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapptest.BuildConfig
import com.example.movieapptest.base.ResultResource
import com.example.movieapptest.data.model.Genre
import com.example.movieapptest.data.model.Genres
import com.example.movieapptest.data.model.MovieDetailModel
import com.example.movieapptest.data.model.Result
import com.example.movieapptest.data.network.ApiConfig
import com.example.movieapptest.data.network.ApiInterface
import com.example.movieapptest.data.repository.paging.MoviePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by sathish on 19,February,2023
 */
class MovieRepositoryImp @Inject constructor(private val apiInterface: ApiInterface) :
    MovieRepository {


    override fun getNowPlaying(): Flow<PagingData<Result>> {
        return Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { MoviePagingSource(apiInterface, "NowPlaying") }
        ).flow
    }

    override fun getPopular(): Flow<PagingData<Result>> {
        return Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { MoviePagingSource(apiInterface, "Popular") }
        ).flow
    }

    override fun getTopRated(): Flow<PagingData<Result>> {
        return Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { MoviePagingSource(apiInterface, "Upcoming") }
        ).flow
    }

    override fun getUpcoming(): Flow<PagingData<Result>> {
        return Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { MoviePagingSource(apiInterface, "TopRated") }
        ).flow
    }

    override suspend fun getGenre(): Flow<List<Genres>> {

        return flow {
            val response = apiInterface.genre(ApiConfig.GENRE + BuildConfig.ApiKey)
            emit(response.body()!!.genres)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getMovieDetail(movieId: String): Flow<MovieDetailModel> {
        return flow {
            val response =
                apiInterface.movieDetail(ApiConfig.MOVIE_DETAIL + movieId + "?api_key=" + BuildConfig.ApiKey)
            emit(response.body()!!)
        }.flowOn(Dispatchers.IO)
    }

    override fun getSearchMovie(searchQuery:String): Flow<PagingData<Result>> {
        return Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { MoviePagingSource(searchQuery,apiInterface, "Search") }
        ).flow
    }

}