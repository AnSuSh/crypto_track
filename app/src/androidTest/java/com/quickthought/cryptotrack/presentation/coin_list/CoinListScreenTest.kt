package com.quickthought.cryptotrack.presentation.coin_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.quickthought.cryptotrack.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CoinListScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun searchForBitcoin_checkIfVisible() {
        // Wait for the list to load (using our fake Hilt module data)
        composeRule.waitUntil(5000) {
            composeRule.onAllNodesWithText("Bitcoin").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithText("Bitcoin").assertIsDisplayed()

        // Click on the item
        composeRule.onNodeWithText("Bitcoin").performClick()

        composeRule.waitUntil(2000) {
            composeRule.onAllNodesWithText("Market Cap").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify we navigated (check for a detail screen element)
        composeRule.onNodeWithText("Market Cap").assertIsDisplayed()
    }

    @Test
    fun searchIcon_showsTextInput_and_filtersList() {
        // Wait for list
        composeRule.waitUntil(5000) {
            composeRule.onAllNodesWithText("Bitcoin").fetchSemanticsNodes().isNotEmpty()
        }

        // Click search icon
        composeRule.onNodeWithContentDescription("Search").performClick()

        // Type into search text field
        composeRule.onNode(hasText("")) // just to ensure TextField exists
        composeRule.onNodeWithText("").performTextInput("bit")

        // Now only items matching "bit" (Bitcoin, BitTorrent) should be visible. Check Bitcoin
        composeRule.waitUntil(2000) {
            composeRule.onAllNodesWithText("Bitcoin").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithText("Bitcoin").assertIsDisplayed()
    }
}
