package net.rjanda.casestudy.nba.test.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.rjanda.casestudy.nba.core.model.data.ContentState
import net.rjanda.casestudy.nba.player.repository.PlayerRepository
import net.rjanda.casestudy.nba.player.screen.list.PlayerListViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

// Very simple example of unit test
@OptIn(ExperimentalCoroutinesApi::class)
class PlayerDtoListViewModelTest {

    private lateinit var viewModel: PlayerListViewModel
    private lateinit var repository: PlayerRepository

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk()
        coEvery { repository.observePlayers() } returns flowOf(emptyList())
        coEvery { repository.observePlayersPaging() } returns flowOf(ContentState.Empty)
        viewModel = PlayerListViewModel(repository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadNextPlayers should call repository loadNextPlayers`() = runTest {
        coEvery { repository.loadNextPlayers() } returns Unit

        viewModel.loadNextPlayers()

        delay(1000)

        coVerify { repository.loadNextPlayers() }
    }

}