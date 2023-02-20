package com.example.movieapptest.ui.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapptest.data.model.Result
import com.example.movieapptest.databinding.ItemMovieBinding
import com.example.movieapptest.ui.moviedetail.MovieDetailActivity
import dagger.hilt.android.qualifiers.ApplicationContext


class MoviesAdapter(private val context: Context) :
    PagingDataAdapter<Result, MoviesAdapter.NotificationViewHolder>(NotificationComparator) {


    object NotificationComparator : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Result, newItem: Result) =
            oldItem == newItem

    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {

        val moviesItem = getItem(position)
        holder.itemMovieBinding.movieModel = moviesItem

        holder.itemView.setOnClickListener {

            val movieDetailScreen = Intent(context, MovieDetailActivity::class.java)
            movieDetailScreen.putExtra("movieId", moviesItem!!.id.toString())
            context.startActivity(movieDetailScreen)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return NotificationViewHolder(binding)
    }


    inner class NotificationViewHolder(var itemMovieBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {


    }


}