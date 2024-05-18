package com.example.api01

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.api01.Model.ResponseMovie
import com.example.api01.databinding.ListMoviesBinding



class MovieAdapter(
        private val data: MutableList<ResponseMovie> = mutableListOf()
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<ResponseMovie>) {
        Log.d("MovieAdapter", "setData called with ${data.size} items")
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class MovieViewHolder(
            private val v: ListMoviesBinding
    ) : RecyclerView.ViewHolder(v.root) {
        fun bind(movie: ResponseMovie) {
            Log.d("MovieAdapter", "bind called with movie: ${movie.title}")
            v.image.load(movie.poster) {
                crossfade(true)
                listener(
                        onError = { _, throwable ->
                            Log.e("ImageLoading", "Error loading image", throwable)
                        }
                )
            }
            v.title.text = movie.title
            v.subhead.text = movie.year
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        Log.d("MovieAdapter", "onCreateViewHolder called")
        return MovieViewHolder(
                ListMoviesBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int {
        Log.d("MovieAdapter", "getItemCount called, returning ${data.size}")
        return data.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Log.d("MovieAdapter", "onBindViewHolder called for position $position")
        val item = data[position]
        holder.bind(item)
    }
}