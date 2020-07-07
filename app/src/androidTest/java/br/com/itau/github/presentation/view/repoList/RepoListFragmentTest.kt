package br.com.itau.github.presentation.view.repoList

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import br.com.itau.github.R
import br.com.itau.github.presentation.MainActivity
import br.com.itau.github.tools.atPosition
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoListFragmentTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testIsRecyclerViewVisibleInAppLaunch() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.repoList)).check(matches(isDisplayed()))
        onView(withId(R.id.loading)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun testRecyclerViewItem(){
        onView(withId(R.id.repoList)).check(matches(atPosition(0, hasDescendant(withText("go")))))
        onView(withId(R.id.repoList)).check(matches(atPosition(0, hasDescendant(withText("The Go programming language")))))
        onView(withId(R.id.repoList)).check(matches(atPosition(0, hasDescendant(withText("golang")))))
        onView(withId(R.id.repoList)).check(matches(atPosition(0, hasDescendant(withText("72894")))))
        onView(withId(R.id.repoList)).check(matches(atPosition(0, hasDescendant(withText("10442")))))
    }

    @Test
    fun goToPrsList(){
        onView(withId(R.id.repoList)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            ViewActions.click()
        ))

        onView(
            allOf(
                instanceOf(TextView::class.java),
                withParent(withId(R.id.toolbar))
            )
        ).check(matches(withText("go")))
    }

    @Test
    fun checkViewContentDescription(){
        onView(withId(R.id.repoList)).check(matches(atPosition(0, hasDescendant(
            withContentDescription(activityRule.activity.getString(R.string.repo_item_accessibility_repo_title,"go"))
        ))))
        onView(withId(R.id.repoList)).check(matches(atPosition(0, hasDescendant(
            withContentDescription(activityRule.activity.getString(R.string.repo_item_accessibility_repo_summary,"The Go programming language"))
        ))))
        onView(withId(R.id.repoList)).check(matches(atPosition(0, hasDescendant(
            withContentDescription(activityRule.activity.getString(R.string.repo_item_accessibility_repo_author,"golang"))
        ))))
        onView(withId(R.id.repoList)).check(matches(atPosition(0, hasDescendant(
            withContentDescription(activityRule.activity.getString(R.string.repo_item_accessibility_repo_star,"72894"))
        ))))
        onView(withId(R.id.repoList)).check(matches(atPosition(0, hasDescendant(
            withContentDescription(activityRule.activity.getString(R.string.repo_item_accessibility_repo_forks,"10442"))
        ))))
    }
}