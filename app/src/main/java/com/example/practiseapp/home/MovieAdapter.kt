package com.example.practiseapp.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.practiseapp.databinding.MovieItemBinding
import com.example.practiseapp.models.MovieItem

class MovieAdapter(private var list: List<MovieItem>): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context))
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = list.count()

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class MovieViewHolder(private val binding: MovieItemBinding): ViewHolder(binding.root) {
        fun bind (movie: MovieItem){
            binding.name.text = movie.name
            binding.imageview.also {
                Glide.with(it.context).load(movie.imageUrl).into(it)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(newList : List<MovieItem>){
        list = newList
        notifyDataSetChanged()
    }
}