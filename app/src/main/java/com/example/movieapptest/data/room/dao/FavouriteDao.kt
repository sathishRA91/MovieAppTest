package com.example.movieapptest.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapptest.data.room.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow


/**
 * Created by sathish on 20,February,2023
 */
@Dao
interface FavouriteDao {

    @Insert
    fun insertFavourite(insertFavouriteEntity: FavouriteEntity)

    @Query("Select * from FavouriteTable")
    fun getAllFavourite(): Flow<List<FavouriteEntity>>


    @Query("Delete from FavouriteTable where movieId = :movieId")
    fun deleteFavourite(movieId:String)

}