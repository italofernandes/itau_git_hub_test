package br.com.itau.github.presentation.view.repoPrs

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import br.com.itau.github.R
import br.com.itau.github.mocks.MocksServer.mockRepoEntity
import br.com.itau.github.presentation.MainActivity
import br.com.itau.github.presentation.repoPrs.view.PrFragment
import br.com.itau.github.presentation.repoPrs.view.REPO_BUNDLE_EXTRA
import br.com.itau.github.tools.atPosition
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PrFragmentTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var scenario: FragmentScenario<PrFragment>

    @Before
    fun init(){
        val fragmentArgs = Bundle().apply {
            putParcelable(REPO_BUNDLE_EXTRA, mockRepoEntity)
        }

        scenario = launchFragmentInContainer<PrFragment>(fragmentArgs, R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testFragInit() {
        onView(withId(R.id.toolbar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.prList))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.loading))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    @Test
    fun testRecyclerViewItem(){
        onView(withId(R.id.prList)).check(
            ViewAssertions.matches(
                atPosition(
                    0,
                    ViewMatchers.hasDescendant(ViewMatchers.withText("Add audience parameter to token request for Auth0 support"))
                )
            )
        )
        onView(withId(R.id.prList)).check(
            ViewAssertions.matches(
                atPosition(
                    0,
                    ViewMatchers.hasDescendant(ViewMatchers.withText("Thank you for adding oidc as an authentication method! \r\n\r\nThis is for making oidc authentication compatible with Auth0\r\nhttps://auth0.com/docs/api-auth/tutorials/client-credentials"))
                )
            )
        )
        onView(withId(R.id.prList)).check(
            ViewAssertions.matches(
                atPosition(
                    0,
                    ViewMatchers.hasDescendant(ViewMatchers.withText("marksteve"))
                )
            )
        )
    }

    @Test
    fun checkViewContentDescription(){
        onView(withId(R.id.prList)).check(
            ViewAssertions.matches(
                atPosition(
                    0, ViewMatchers.hasDescendant(
                        ViewMatchers.withContentDescription(
                            activityRule.activity.getString(
                                R.string.pr_item_accessibility_repo_title,
                                "Add audience parameter to token request for Auth0 support"
                            )
                        )
                    )
                )
            )
        )
        onView(withId(R.id.prList)).check(
            ViewAssertions.matches(
                atPosition(
                    0, ViewMatchers.hasDescendant(
                        ViewMatchers.withContentDescription(
                            activityRule.activity.getString(
                                R.string.pr_item_accessibility_repo_summary,
                                "Thank you for adding oidc as an authentication method! \r\n\r\nThis is for making oidc authentication compatible with Auth0\r\nhttps://auth0.com/docs/api-auth/tutorials/client-credentials"
                            )
                        )
                    )
                )
            )
        )
        onView(withId(R.id.prList)).check(
            ViewAssertions.matches(
                atPosition(
                    0, ViewMatchers.hasDescendant(
                        ViewMatchers.withContentDescription(
                            activityRule.activity.getString(
                                R.string.pr_item_accessibility_repo_author,
                                "marksteve"
                            )
                        )
                    )
                )
            )
        )
    }
}