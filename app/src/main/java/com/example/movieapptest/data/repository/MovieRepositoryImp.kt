package com.example.movieapptest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapptest.data.model.Genres
import com.example.movieapptest.data.model.MovieDetailModel
import com.example.movieapptest.data.model.Result
import com.example.movieapptest.data.network.ApiInterface
import com.example.movieapptest.data.repository.paging.MoviePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by sathish on 19,February,2023
 */
class MovieRepositoryImp @Inject constructor(private val apiInterface: ApiInterface) : MovieRepository {
    override  fun getNowPlaying(): Flow<PagingData<Result>> {
       return Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { MoviePagingSource(apiInterface,"NowPlaying") }
        ).flow
    }

    override  fun getPopular(): Flow<PagingData<Result>> {
        return Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { MoviePagingSource(apiInterface,"Popular") }
        ).flow
    }

    override  fun getTopRated(): Flow<PagingData<Result>> {
        return Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { MoviePagingSource(apiInterface,"Upcoming") }
        ).flow
    }

    override  fun getUpcoming(): Flow<PagingData<Result>> {
        return Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { MoviePagingSource(apiInterface,"TopRated") }
        ).flow
    }

    override suspend fun getGenre(): List<Genres> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetail(): MovieDetailModel {
        TODO("Not yet implemented")
    }

    override  fun getSearchMovie(): Flow<PagingData<Result>> {
        return Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { MoviePagingSource(apiInterface,"Search") }
        ).flow
    }

}