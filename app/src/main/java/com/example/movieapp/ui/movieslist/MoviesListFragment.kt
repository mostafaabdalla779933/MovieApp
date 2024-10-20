package com.example.movieapp.ui.movieslist


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movieapp.common.BaseFragment
import com.example.movieapp.common.BaseViewState
import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.databinding.FragmentMoviesListBinding
import com.example.movieapp.ui.moviedetials.MovieDetailsFragment.Companion.MOVIE_KEY
import com.example.moviesapp.ui.movieslist.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviesListFragment : BaseFragment<FragmentMoviesListBinding, MoviesListVM>(FragmentMoviesListBinding::inflate) {

    private val adapter : MoviesAdapter by lazy {
        MoviesAdapter(
            onclick = {
                findNavController().navigate(MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(it))
            },
            onFavoriteClick = { movie, isFavorite ->
                if (isFavorite) {
                    viewModel.addMovieToFavorites(movie)
                } else {
                    viewModel.removeMovieFromFavorites(movie)
                }
            }
        )
    }
    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[MoviesListVM::class.java]
    }

    override fun onFragmentCreated() {
        viewModel.getMoviesList()
        binding.rvMovies.adapter = adapter

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<MovieModel>(MOVIE_KEY)?.observe(
            viewLifecycleOwner) { movie ->
            adapter.updateMovie(movie)
        }
    }

    override fun renderView(viewState: BaseViewState?) {

        when(viewState){
            is MoviesViewState.MoviesList -> {
                adapter.setMovieList(viewState.list.toMutableList())
            }
        }
    }

}