package com.example.movieapptest.ui.moviesearch

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapptest.R
import com.example.movieapptest.base.AppConstant
import com.example.movieapptest.data.model.Genres
import com.example.movieapptest.databinding.ActivityMovieSearchBinding
import com.example.movieapptest.ui.adapter.MoviesAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by sathish on 20,February,2023
 */

@AndroidEntryPoint
class SearchMovieActivity : AppCompatActivity() {

    private lateinit var activityMovieSearchBinding: ActivityMovieSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var movieAdapter: MoviesAdapter

    @Inject
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMovieSearchBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_movie_search)
        activityMovieSearchBinding.searchViewModel = searchViewModel
        activityMovieSearchBinding.lifecycleOwner = this
        activityMovieSearchBinding.executePendingBindings()
        initEvent()
    }

    private fun initEvent() {

        movieAdapter = MoviesAdapter(this)
        activityMovieSearchBinding.RvSearch.apply {
            layoutManager = LinearLayoutManager(this@SearchMovieActivity)
            adapter = movieAdapter
        }

        lifecycleScope.launch {
            val genreList = preferences.getString(AppConstant.GENRE_ITEM, null)
            searchViewModel.getSearchMovieList.collectLatest { pagingData ->

                val pagingResult = pagingData.map { result ->
                    val genreCategory = ArrayList<String>()
                    result.genre_ids.forEach { genreId ->
                        if (genreList != null) {
                            val genreData: List<Genres> =
                                Gson().fromJson(
                                    genreList,
                                    object : TypeToken<List<Genres>>() {}.type
                                )

                            for (i in genreData.indices) {
                                if (genreData[i].id == genreId) {
                                    genreCategory.add(genreData[i].name)
                                }
                            }
                        }
                        result.genre_categoryName = genreCategory
                    }
                    result
                }


                movieAdapter.submitData(pagingResult)
            }

        }


        activityMovieSearchBinding.IvBack.setOnClickListener { finish() }
    }


}