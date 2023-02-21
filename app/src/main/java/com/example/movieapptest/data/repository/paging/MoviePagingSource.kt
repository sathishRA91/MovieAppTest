package com.example.movieapptest.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapptest.BuildConfig
import com.example.movieapptest.data.model.Result
import com.example.movieapptest.data.network.ApiInterface

class MoviePagingSource (private val apiInterface: ApiInterface, private val screenFrom: String) :
    PagingSource<Int, Result>() {

    private var searchQuery: String?=""

    constructor(searchQuery:String, apiInterface: ApiInterface, screenFrom: String):this(apiInterface,screenFrom)
    {
        this.searchQuery=searchQuery
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {

        return try {
            val pageNumber = params.key ?: 1

            val response = when (screenFrom) {
                "NowPlaying" -> {
                    apiInterface.nowPlaying(pageNumber, BuildConfig.ApiKey)
                }
                "Popular" -> {
                    apiInterface.popular(pageNumber, BuildConfig.ApiKey)
                }
                "Upcoming" -> {
                    apiInterface.upcoming(pageNumber, BuildConfig.ApiKey)
                }
                "TopRated" -> {
                    apiInterface.topRated(pageNumber, BuildConfig.ApiKey)
                }
                else -> {
                    apiInterface.search(searchQuery.toString(),pageNumber, BuildConfig.ApiKey)
                }
            }


            LoadResult.Page(
                data = response.body()!!.results,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = pageNumber + 1
            )
        } catch (e: Exception) {

            LoadResult.Error(e)
        }

    }

}