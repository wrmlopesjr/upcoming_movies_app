package com.arctouch.codechallenge.feature

import com.arctouch.codechallenge.TestConstants
import com.arctouch.codechallenge.base.*
import com.arctouch.codechallenge.base.mock.api.ResponseHandler
import com.arctouch.codechallenge.features.home.HomeViewModel
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import junit.framework.Assert.assertEquals
import okhttp3.Request
import org.junit.Test
import org.koin.test.get


class HomeViewModelTest : BaseTest() {

    val emptyResponse = UpcomingMoviesResponse(3, ArrayList(), 2, 38)

    val genreAction = Genre(28, "Action")
    val genreComedy = Genre(35, "Comedy")

    @Test
    fun testGetUpcoming() {

        val viewModel: HomeViewModel = TestSuite.get()

        val results = viewModel.movies.value!!

        //validate results size
        assert(results.size == 20)

        //validate genres
        assertEquals(listOf(genreAction), results[1].genres)
        assertEquals(listOf(genreAction, genreComedy), results[2].genres)
        assert(results[3].genres!!.isEmpty())

        //validate request success
        assertEquals(NetworkState.SUCCESS, viewModel.networkState.value)

        //validate disposable cleaning
        viewModel.onCleared()
        assert(viewModel.getDisposables().isEmpty())
    }

    @Test
    fun testGetUpcomingPagination() {

        TestSuite.mock(TestConstants.upcomingURL).body(object : ResponseHandler {
            override fun getResponse(request: Request, path: String): String {
                val page = request.url().queryParameter("page")!!
                when (page) {
                    "1" -> {
                        return FileUtils.readJson(path.substring(1) + ".json")!!
                    }
                    "2" -> {
                        return FileUtils.readJson(path.substring(1) + "-page2.json")!!
                    }
                    else -> {
                        return JsonUtils.toJson(emptyResponse)
                    }
                }
            }
        }).apply()

        val viewModel: HomeViewModel = TestSuite.get()

        //first page already loaded, so request the second one
        viewModel.load(2)

        //validate results size
        assert(viewModel.movies.value!!.size == 38)

        //load third page (should be empty, so no changes to results)
        viewModel.load(3)

        //validate results size
        assert(viewModel.movies.value!!.size == 38)

        //validate request success
        assertEquals(NetworkState.SUCCESS, viewModel.networkState.value)
    }

    @Test
    fun testGetUpcomingGenreRequestError() {
        TestSuite.mock(TestConstants.genresURL).throwConnectionError().apply()

        val viewModel: HomeViewModel = TestSuite.get()

        //validate request error
        assertEquals(NetworkState.ERROR, viewModel.networkState.value)
    }

    @Test
    fun testGetUpcomingRequestError() {
        TestSuite.mock(TestConstants.upcomingURL).throwConnectionError().apply()

        val viewModel: HomeViewModel = TestSuite.get()

        //validate request error
        assertEquals(NetworkState.ERROR, viewModel.networkState.value)
    }

    @Test
    fun testGetUpcomingRequestErrorOnSecondPage() {
        val viewModel: HomeViewModel = TestSuite.get()

        //validate request success
        assertEquals(NetworkState.SUCCESS, viewModel.networkState.value)

        TestSuite.mock(TestConstants.upcomingURL).throwConnectionError().apply()

        viewModel.load(2)

        //validate request error
        assertEquals(NetworkState.ERROR, viewModel.networkState.value)
    }

    @Test
    fun testGetUpcomingEmptyOnFirstPage() {
        TestSuite.mock(TestConstants.upcomingURL).body(JsonUtils.toJson(emptyResponse)).apply()

        val viewModel: HomeViewModel = TestSuite.get()

        //validate request empty
        assertEquals(NetworkState.EMPTY, viewModel.networkState.value)
    }

}