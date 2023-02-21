package com.example.movieapptest.data.repository

import com.example.movieapptest.data.room.dao.FavouriteDao
import com.example.movieapptest.data.room.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by sathish on 21,February,2023
 */
class FavouriteRepositoryImp @Inject constructor(private val dao: FavouriteDao):FavouriteRepository {
    override suspend fun insertFavourite(favouriteEntity: FavouriteEntity) {
        dao.insertFavourite(favouriteEntity)
    }

    override suspend fun getAllFavourite(): Flow<List<FavouriteEntity>> {
        return dao.getAllFavourite()
    }

    override suspend fun checkFavouriteExist(movieId: String): FavouriteEntity? {
        return dao.checkFavouriteIsExist(movieId)
    }

    override suspend fun deleteFavourite(movieId: String) {
        dao.deleteFavourite(movieId)
    }
}