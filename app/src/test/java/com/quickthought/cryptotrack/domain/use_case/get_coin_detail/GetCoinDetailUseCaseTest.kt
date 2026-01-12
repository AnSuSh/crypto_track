package com.quickthought.cryptotrack.domain.use_case.get_coin_detail

import app.cash.turbine.test
import com.quickthought.cryptotrack.core.Resource
import com.quickthought.cryptotrack.domain.model.ChartData
import com.quickthought.cryptotrack.domain.model.Coin
import com.quickthought.cryptotrack.domain.repository.CoinRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetCoinDetailUseCaseTest {

    private lateinit var useCase: GetCoinDetailUseCase
    private val repository: CoinRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetCoinDetailUseCase(repository)
    }

    @Test
    fun `UseCase should emit Loading then Success when both repository calls succeed`() = runTest {
        val fakeCoin = Coin("btc", "Bitcoin", "BTC", 50000.0, 1.0, "")
        val fakeChart = ChartData(listOf(49000f, 50000f))

        // Stubbing both repository calls
        coEvery { repository.getCoinById("btc") } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(fakeCoin))
        }
        coEvery { repository.getCoinChart("btc") } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(fakeChart))
        }

        useCase("btc").test {
            // First item emitted by zip/flow logic
            assert(awaitItem() is Resource.Loading)

            val result = awaitItem()
            assert(result is Resource.Success)
            assert(result.data?.coin?.name == "Bitcoin")
            assert(result.data?.chartData?.yValues?.size == 2)
            awaitComplete()
        }
    }
}