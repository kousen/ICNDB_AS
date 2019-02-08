package com.nfjs.icndb.app;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void jokeButtonUsesHeroName() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withId(R.id.icndb_button))
                .perform(click());
        onView(withId(R.id.text_view))
                .check(matches(withText(containsString(
                        prefs.getString("first", "Hans")))));
    }
}
