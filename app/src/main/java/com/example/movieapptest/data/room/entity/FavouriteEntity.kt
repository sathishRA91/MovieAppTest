package com.example.movieapptest.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by sathish on 19,February,2023
 */
@Entity(tableName = "FavouriteTable")
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val poster: String,
    val title: String,
    val genre: String,
    val releaseDate: String,
    val voteAverage: String,
    val voteCount: String,
    val movieId:String
) {
}