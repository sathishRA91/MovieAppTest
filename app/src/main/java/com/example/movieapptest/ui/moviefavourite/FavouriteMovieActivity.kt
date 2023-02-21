package com.example.movieapptest.ui.moviefavourite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapptest.databinding.ActivityMovieFavouriteBinding
import com.example.movieapptest.ui.adapter.FavouriteAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by sathish on 20,February,2023
 */
@AndroidEntryPoint
class FavouriteMovieActivity : AppCompatActivity() {

    private lateinit var activityMovieFavouriteBinding: ActivityMovieFavouriteBinding
    private val favouriteViewModel: FavouriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMovieFavouriteBinding = ActivityMovieFavouriteBinding.inflate(layoutInflater)
        setContentView(activityMovieFavouriteBinding.root)

        activityMovieFavouriteBinding.RvSearch.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            favouriteViewModel.favoriteResult.collectLatest {

                if(it.isNotEmpty()) {
                    activityMovieFavouriteBinding.RvSearch.adapter =
                        FavouriteAdapter(this@FavouriteMovieActivity, it)
                }
            }

        }
        activityMovieFavouriteBinding.IvBack.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()


    }
}