package com.example.findpix.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchBoxTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun setAndCheckTheTextFieldValue() {
        ActivityScenario.launch(MainActivity::class.java)

        val resultText1 = "result1"
        val resultText2 = "result2"

        // Clear text field
        composeTestRule.onNodeWithTag("ClearBtn").performClick()

        // Input a text
        composeTestRule.onNodeWithTag("SearchBox").performTextInput(resultText1)

        // Assertion
        composeTestRule.onNodeWithTag("SearchBox").assert(hasText(resultText1))

        // Clear text field
        composeTestRule.onNodeWithTag("ClearBtn").performClick()

        // Input a text
        composeTestRule.onNodeWithTag("SearchBox").performTextInput(resultText2)

        // Assertion
        composeTestRule.onNodeWithTag("SearchBox").assert(hasText(resultText2))
    }
}
