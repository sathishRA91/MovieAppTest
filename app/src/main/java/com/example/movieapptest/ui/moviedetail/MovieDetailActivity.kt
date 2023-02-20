package com.example.movieapptest.ui.moviedetail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.movieapptest.R
import com.example.movieapptest.databinding.ActivityMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by sathish on 20,February,2023
 */
@AndroidEntryPoint
class MovieDetailActivity :AppCompatActivity() {

    private lateinit var activityMovieDetailBinding:ActivityMovieDetailBinding
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMovieDetailBinding=DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        activityMovieDetailBinding.movieDetail=movieDetailViewModel
        activityMovieDetailBinding.lifecycleOwner=this
        activityMovieDetailBinding.executePendingBindings()

    }

}