package com.quickthought.cryptotrack

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class E2EFlowTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        hiltRule.inject()
        mockWebServer.start(8080)
        // You would need to point your Base URL to localhost:8080 in the TestModule
    }

    @Test
    fun app_full_flow_test() {
        // 1. PREPARE: Mock the List API response
        val listJson =
            """[{"id":"bitcoin","symbol":"btc","name":"Bitcoin","image":"url","current_price":50000.0,"price_change_percentage_24h":2.5}]"""
        mockWebServer.enqueue(MockResponse().setBody(listJson).setResponseCode(200))

        // 2. PREPARE: Mock the Detail/Chart API response (for the next screen)
        val chartJson = """{"prices":[[1700000000,49000.0],[1700000001,50000.0]]}"""
        mockWebServer.enqueue(MockResponse().setBody(chartJson).setResponseCode(200))

        // 3. EXECUTE: Check if List Screen loads data correctly
        // We use wait because the Shimmer is showing initially
        composeRule.waitUntil(5000) {
            composeRule.onAllNodesWithText("Bitcoin").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText("Bitcoin").assertIsDisplayed()

        // 4. INTERACT: Navigate to Detail
        composeRule.onNodeWithText("Bitcoin").performClick()

        // 5. VERIFY: Detail Screen elements and Chart visibility
        composeRule.waitUntil(5000) {
            composeRule.onAllNodesWithText("Market Cap").fetchSemanticsNodes().isNotEmpty()
        }

        // Assert that the Price History text and the Rank/Stats are visible
        composeRule.onNodeWithText("Market Cap").assertIsDisplayed()

        // Advanced: Use a tag to check if the Canvas Chart is actually drawn
        composeRule.onNodeWithTag("coin_chart_canvas").assertExists()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}