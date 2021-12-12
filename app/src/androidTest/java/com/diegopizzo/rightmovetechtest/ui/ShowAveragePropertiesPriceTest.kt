package com.diegopizzo.rightmovetechtest.ui

import androidx.compose.ui.semantics.ProgressBarRangeInfo.Companion.Indeterminate
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import com.diegopizzo.rightmovetechtest.ui.component.ShowAveragePropertiesPrice
import com.diegopizzo.rightmovetechtest.ui.theme.RightmoveTechTestTheme
import org.junit.Rule
import org.junit.Test

class ShowAveragePropertiesPriceTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setContent(average: String?, isLoading: Boolean?) {
        composeTestRule.setContent {
            RightmoveTechTestTheme {
                ShowAveragePropertiesPrice(average, isLoading)
            }
        }
    }

    @Test
    fun showAveragePrice_isLoading_averageIsEmpty() {
        setContent(average = "", isLoading = true)

        composeTestRule.onNode(hasText("What is the average property price?")).assertExists()
        composeTestRule.onNode(hasText("")).assertExists()
        composeTestRule.onNode(hasProgressBarRangeInfo(Indeterminate)).assertExists()
    }

    @Test
    fun showAveragePrice_isLoadedWithSuccess_averageIsShown() {
        setContent(average = "123456.98", isLoading = false)

        composeTestRule.onNode(hasText("What is the average property price?")).assertExists()
        composeTestRule.onNode(hasText("123456.98")).assertExists()
        composeTestRule.onNode(hasProgressBarRangeInfo(Indeterminate)).assertDoesNotExist()
    }

    @Test
    fun showAveragePrice_isLoadedWithError_averageIsErrorMessageIsShown() {
        setContent(average = null, isLoading = false)
        composeTestRule.onNode(hasText("What is the average property price?")).assertExists()
        composeTestRule.onNode(hasText("Average not available")).assertExists()
        composeTestRule.onNode(hasProgressBarRangeInfo(Indeterminate)).assertDoesNotExist()
    }
}