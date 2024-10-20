package com.example.movieapp.ui.moviedetials


import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.common.BaseFragment
import com.example.movieapp.common.BaseViewState
import com.example.movieapp.common.getLoadingDrawable
import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding, MovieDetailsVM>(FragmentMovieDetailsBinding::inflate) {

    private val args: MovieDetailsFragmentArgs by navArgs()
    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[MovieDetailsVM::class.java]
    }

    override fun onFragmentCreated() {
        bindMovieDetails(args.movie)
    }

    override fun renderView(viewState: BaseViewState?) {

    }

    private fun bindMovieDetails(movie: MovieModel) {
        binding.apply {
            movie.title?.let { title -> tvMovieTitle.text = title }
            movie.releaseDate?.let { date -> tvDate.text = date }
            ivFavorite.isSelected = movie.isFavorite
            movie.voteAverage?.let { rate -> tvRate.text = "Rate: ${rate.toString()}" }
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/original/" + movie.posterPath)
                .placeholder(requireContext().getLoadingDrawable())
                .error(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_error_loading
                    )
                )
                .into(ivMoviePoster)

            ivFavorite.setOnClickListener {
                if (ivFavorite.isSelected) {
                    viewModel.removeMovieFromFavorites(movie)
                } else {
                    viewModel.addMovieToFavorites(movie)
                }
                ivFavorite.isSelected = !ivFavorite.isSelected
                findNavController().previousBackStackEntry?.savedStateHandle?.set(MOVIE_KEY, args.movie.apply { isFavorite = !isFavorite })
            }
        }

    }


    companion object {
        const val MOVIE_KEY = "MOVIE_KEY"
    }

}