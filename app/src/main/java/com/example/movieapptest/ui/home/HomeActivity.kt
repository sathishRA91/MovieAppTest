package com.example.movieapptest.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movieapptest.R
import com.example.movieapptest.databinding.ActivityHomeBinding
import com.example.movieapptest.ui.moviefavourite.FavouriteMovieActivity
import com.example.movieapptest.ui.moviesearch.SearchMovieActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var activityHomeBinding: ActivityHomeBinding
    private lateinit var navController: NavController
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        activityHomeBinding.homeViewModel=homeViewModel
        activityHomeBinding.lifecycleOwner=this
        activityHomeBinding.executePendingBindings()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        activityHomeBinding.navigationView.setupWithNavController(navController)

        activityHomeBinding.IvSearch.setOnClickListener(this)
        activityHomeBinding.IvFavourite.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v!!.id)
        {
            R.id.Iv_search->
            {
                startActivity(Intent(this,SearchMovieActivity::class.java))
            }

            R.id.Iv_favourite ->
            {
                startActivity(Intent(this,FavouriteMovieActivity::class.java))
            }

        }
    }


}