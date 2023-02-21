package com.example.movieapptest.data.repository

import com.example.movieapptest.data.room.dao.FavouriteDao
import com.example.movieapptest.data.room.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by sathish on 21,February,2023
 */
interface FavouriteRepository {

    suspend fun insertFavourite(favouriteEntity: FavouriteEntity)

    suspend fun getAllFavourite():Flow<List<FavouriteEntity>>

    suspend fun checkFavouriteExist(movieId:String):FavouriteEntity?

    suspend fun deleteFavourite(movieId:String)
}