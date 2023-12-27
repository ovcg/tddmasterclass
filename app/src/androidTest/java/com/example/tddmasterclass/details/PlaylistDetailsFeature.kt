package com.example.tddmasterclass.details

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.tddmasterclass.BaseUITest
import com.example.tddmasterclass.R
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class PlaylistDetailsFeature : BaseUITest() {

    @Test
    fun displayPlaylistNameAndDetails() {
        navigateToPlaylistDetails(0)

        assertDisplayed("Hard Rock Cafe")

        assertDisplayed("Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")
    }

    @Test
    fun displaysErrorMessageWhenNetworkFails() {
        navigateToPlaylistDetails(1)

        assertDisplayed(R.string.generic_error)
    }

    private fun navigateToPlaylistDetails(position: Int) {
        onView(
            CoreMatchers.allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(
                    nthChildOf(
                        withId(R.id.playlists_list),
                        position
                    )
                )
            )
        ).perform(ViewActions.click())
    }
}