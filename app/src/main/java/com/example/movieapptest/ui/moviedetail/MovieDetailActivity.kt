package com.example.movieapptest.ui.moviedetail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.movieapptest.R
import com.example.movieapptest.base.ResultResource
import com.example.movieapptest.databinding.ActivityMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by sathish on 20,February,2023
 */
@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var activityMovieDetailBinding: ActivityMovieDetailBinding
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private lateinit var movieId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMovieDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        activityMovieDetailBinding.movieDetail = movieDetailViewModel
        activityMovieDetailBinding.lifecycleOwner = this
        activityMovieDetailBinding.executePendingBindings()
        initEvent()
    }

    private fun initEvent() {
        movieId = intent.getStringExtra("movieId").toString()
        movieDetailViewModel.movieDetail(movieId)

        activityMovieDetailBinding.IvBack.setOnClickListener(this)
        activityMovieDetailBinding.IvFavourite.setOnClickListener(this)

        lifecycleScope.launch {
            movieDetailViewModel.resultResponse.collectLatest {
                when (it) {
                    is ResultResource.Loading -> {
                        activityMovieDetailBinding.PbLoading.isVisible = true
                    }
                    else -> {
                        activityMovieDetailBinding.PbLoading.isVisible = false
                    }
                }

            }
        }

    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.Iv_back -> {
                finish()
            }
            R.id.Iv_favourite -> {
                movieDetailViewModel.addRemoveFavourite(movieId)
            }
        }
    }

}