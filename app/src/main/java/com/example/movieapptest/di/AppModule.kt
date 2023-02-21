package com.example.movieapptest.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.movieapptest.BuildConfig
import com.example.movieapptest.base.AppConstant
import com.example.movieapptest.data.network.ApiInterface
import com.example.movieapptest.data.repository.FavouriteRepository
import com.example.movieapptest.data.repository.FavouriteRepositoryImp
import com.example.movieapptest.data.repository.MovieRepository
import com.example.movieapptest.data.repository.MovieRepositoryImp
import com.example.movieapptest.data.repository.paging.MoviePagingSource
import com.example.movieapptest.data.room.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .connectTimeout(100L, TimeUnit.SECONDS)
            .readTimeout(100L, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BaseUrl)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(apiInterface:ApiInterface):MovieRepository
    {
        return MovieRepositoryImp(apiInterface)
    }



    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): DataBase {
        return Room.databaseBuilder(
            appContext,
            DataBase::class.java, "movieAppTest.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavouriteRepository(db:DataBase):FavouriteRepository
    {
        return FavouriteRepositoryImp(db.favouriteDao)
    }


    @Singleton
    @Provides
    fun providePreference(@ApplicationContext appContext: Context): SharedPreferences
    {
        return appContext.getSharedPreferences(AppConstant.MOVIE_APP_PREFERENCE, Context.MODE_PRIVATE)
    }
}