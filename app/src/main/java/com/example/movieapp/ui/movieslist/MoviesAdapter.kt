package com.example.moviesapp.ui.movieslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.common.getLoadingDrawable
import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.databinding.ItemMovieBinding


class MoviesAdapter(private var list: MutableList<MovieModel?> = mutableListOf(), val onclick: (MovieModel) -> Unit, val onFavoriteClick: (MovieModel, Boolean) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MoviesViewHolder(private val rowView: ItemMovieBinding) :
        RecyclerView.ViewHolder(rowView.root) {
        fun onBind(item: MovieModel, position: Int) {
            rowView.apply {
                item.title?.let { title -> tvMovieTitle.text = title }
                item.releaseDate?.let { date -> tvDate.text = date }
                ivFavorite.isSelected = item.isFavorite
                item.voteAverage?.let { rate -> tvRate.text = "Rate: ${rate.toString()}" }
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/original/" + item.posterPath)
                    .placeholder(itemView.context.getLoadingDrawable())
                    .error(
                        AppCompatResources.getDrawable(
                            itemView.context,
                            R.drawable.ic_error_loading
                        )
                    )
                    .into(ivMoviePoster)
                ivFavorite.setOnClickListener {
                    onFavoriteClick(item,!ivFavorite.isSelected)
                    list[position]?.isFavorite = !(list[position]?.isFavorite ?: false)
                    notifyItemChanged(position)
                }
                root.setOnClickListener {
                    onclick(item)
                }
            }
        }
    }

    fun updateMovie(movie: MovieModel){
        list.find { it?.id == movie.id }?.isFavorite = movie.isFavorite
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MoviesViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MoviesViewHolder -> {
                list[position]?.let { holder.onBind(it, position) }
            }
        }
    }

    fun setMovieList(list: MutableList<MovieModel?>) {
        this.list = list
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = list.size

}