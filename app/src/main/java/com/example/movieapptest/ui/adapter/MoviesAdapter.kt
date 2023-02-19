package com.example.movieapptest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapptest.data.model.Result
import com.example.movieapptest.databinding.ItemMovieBinding


class MoviesAdapter:PagingDataAdapter<Result,MoviesAdapter.NotificationViewHolder>(NotificationComparator) {


    object NotificationComparator:DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Result, newItem: Result) =
            oldItem == newItem

    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {

        val moviesItem = getItem(position)

        holder.itemMovieBinding.movieModel = moviesItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return NotificationViewHolder(binding)
    }


    inner  class NotificationViewHolder(var itemMovieBinding: ItemMovieBinding):RecyclerView.ViewHolder(itemMovieBinding.root) {


    }


}