package com.example.movieapptest.data.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapptest.data.room.dao.FavouriteDao
import com.example.movieapptest.data.room.entity.FavouriteEntity


@Database(entities = [FavouriteEntity::class], version = 1)
abstract class DataBase:RoomDatabase() {

    abstract val favouriteDao:FavouriteDao
}