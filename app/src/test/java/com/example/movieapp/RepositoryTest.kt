package com.example.movieapp

import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.data.model.MovieEntity
import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.data.model.MoviesResponse
import com.example.movieapp.data.remote.ApiService
import com.example.movieapp.data.repo.MoviesRepoImp
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class RepositoryTest {

    private lateinit var repository: MoviesRepoImp
    private lateinit var apiService: ApiService
    private lateinit var movieDAO: MovieDao

    @Before
    fun setUp() {
        apiService = mockk(relaxed = true)
        movieDAO = mockk(relaxed = true)
        repository = MoviesRepoImp(apiService, movieDAO, TestCoroutineDispatcher())
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun test_getMoviesList_should_return_data_from_api() = runBlocking {

        val moviesModel = listOf(MovieModel(id = 0,title ="Movie 1"), MovieModel(id = 1,title ="Movie 2"))
        val moviesEntity = listOf(MovieEntity(id = 0,title ="Movie 1"), MovieEntity(id = 1,title ="Movie 2"))
        coEvery { movieDAO.getAllMovies() } returns flowOf( moviesEntity)
        coEvery { apiService.getMoviesList() } returns Response.success(MoviesResponse(results = moviesModel))

        val flow: Flow<Response<MoviesResponse>> = repository.getMoviesList()

        flow.collect { response ->
            assert(response.isSuccessful)
            assert(response.body()?.results  == moviesModel)
        }
        coVerify(exactly = 1) { apiService.getMoviesList() }
    }



    @Test
    fun test_Add_Movie_To_Favorites() = runBlocking {
        val movie = MovieEntity(id = 1, title = "Movie 1")
        coEvery { movieDAO.addMovieItem(movie) } returns 1L

        val result = repository.addMovieToFavorites(movie).first()

        assert(result == 1L)
        verify { movieDAO.addMovieItem(movie) }
    }

    @Test
    fun test_Remove_Movie_From_Favorites() = runBlocking {
        val movie = MovieEntity(id = 1, title = "Movie 1")
        coEvery { movieDAO.deleteMovieById(1) } returns 1

        val result = repository.removeMovieFromFavorites(movie).first()

        assert(result == 1)
        verify { movieDAO.deleteMovieById(1) }
    }

    @Test
    fun test_Get_All_Favorites_Movies() = runBlocking {

        val movieList = listOf(MovieEntity(id = 1, title = "Movie 1"))
        coEvery { movieDAO.getAllMovies() } returns flowOf(movieList)

        val result = repository.getAllFavoritesMovies().first()

        assert(result == movieList)
        verify { movieDAO.getAllMovies() }
    }


}
