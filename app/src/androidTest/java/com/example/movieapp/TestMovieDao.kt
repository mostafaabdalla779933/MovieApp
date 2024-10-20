package com.example.movieapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movieapp.data.local.MovieAppDatabase
import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.data.model.MovieEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException



@RunWith(AndroidJUnit4::class)
class TestMovieDao {
    private lateinit var movieDao: MovieDao
    private lateinit var db: MovieAppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MovieAppDatabase::class.java).allowMainThreadQueries().build()
        movieDao = db.movieDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    fun insert_Movie_And_Read_It_From_The_List() = runBlocking {
        val movie =  MovieEntity(title = "title",  overview= "overview", id = 100)
        movieDao.addMovieItem(movie)
        assert(movieDao.getAllMovies().first().contains(movie))
    }

    @Test
    fun test_Conflict_Add_Two_Item_With_The_Same_Id(): Unit = runBlocking{
        val movies = listOf(MovieEntity(title = "title1", id = 1),
            MovieEntity(title = "title2",id = 1))

        movieDao.addMovieItem(movies[0])
        movieDao.addMovieItem(movies[1])
        movieDao.getAllMovies().let{
            assert(!it.first().contains(movies[0]))
            assert(it.first().contains(movies[1]))
        }
    }
}