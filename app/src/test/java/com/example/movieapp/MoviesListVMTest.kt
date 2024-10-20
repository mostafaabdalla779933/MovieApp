package com.example.movieapp

import com.example.movieapp.common.Loader
import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.domain.usecase.FavoriteMovieUseCase
import com.example.movieapp.domain.usecase.GetMoviesUseCase
import com.example.movieapp.ui.movieslist.MoviesListVM
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.concurrent.CountDownLatch


@ExperimentalCoroutinesApi
class HomeVMTest {


    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    @Test
    fun `test getMoviesList`() = runBlockingTest {
        val getMoviesUseCase = mockk<GetMoviesUseCase>()
        val favMoviesUseCase = mockk<FavoriteMovieUseCase>()
        val homeVM = MoviesListVM(getMoviesUseCase,favMoviesUseCase)

        val moviesResponse = flowOf(listOf(MovieModel(id=1, title = "movie 1")))

        coEvery { getMoviesUseCase.invoke() } returns moviesResponse

        val observer =  homeVM.internalState.testFlowObserver(mainCoroutineRule)

        homeVM.getMoviesList()

        val state = observer.awaitValue()
        assertEquals(Loader.Progress(false), state)
    }
}


@ExperimentalCoroutinesApi
class MainCoroutineRule(
    val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher) {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}

inline fun <reified T : Any> Flow<T>.testFlowObserver(testScope: CoroutineScope): FlowTestObserver<T> {
    return FlowTestObserver<T>().also { observer ->
        testScope.launch {
            collect { value -> observer.onChanged(value) }
        }
    }
}

class FlowTestObserver<T> {
    private val latch = CountDownLatch(1)
    val values = mutableListOf<T>()

    fun onChanged(newValue: T) {
        values.add(newValue)
        latch.countDown()
    }

    suspend fun awaitValue(): T {
        if (values.isNotEmpty()) {
            return values.last()
        }

        latch.await()
        return values.last()
    }
}




