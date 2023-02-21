package com.example.movieapptest.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapptest.R
import com.example.movieapptest.data.room.entity.FavouriteEntity
import com.example.movieapptest.databinding.ItemFavouriteBinding
import com.example.movieapptest.ui.moviedetail.MovieDetailActivity


class FavouriteAdapter(
    private val context: Context,
    private val favouriteList: List<FavouriteEntity>,
) :
    RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding: ItemFavouriteBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_favourite,
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val favouriteItem = favouriteList[i]

        viewHolder.itemRowBinding.favourite = favouriteItem


        viewHolder.itemView.setOnClickListener {
            val movieDetailScreen = Intent(context, MovieDetailActivity::class.java)
            movieDetailScreen.putExtra("movieId", favouriteItem.movieId.toString())
            context.startActivity(movieDetailScreen)

        }

    }

    override fun getItemCount(): Int {
        return favouriteList.size
    }

    inner class ViewHolder(var itemRowBinding: ItemFavouriteBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {}
}