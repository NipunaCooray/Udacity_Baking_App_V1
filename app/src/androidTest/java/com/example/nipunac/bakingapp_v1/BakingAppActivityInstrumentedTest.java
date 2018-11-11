package com.example.nipunac.bakingapp_v1;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class BakingAppActivityInstrumentedTest {

    private IdlingResource mIdlingResource;

    @Rule public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource()
    {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void mainActivityBasicTest()
    {
        onView(withId(R.id.RecipeRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void RecipeDetailsActivityBasicTest()
    {
        mainActivityBasicTest();
        onView(withId(R.id.recipe_steps_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @After
    public void unregisterIdlingResource()
    {
        if (mIdlingResource != null)
        {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
