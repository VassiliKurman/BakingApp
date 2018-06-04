/*
* Copyright (C) 2018 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package vkurman.bakingapp;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import vkurman.bakingapp.IdlingResource.RecipeCountingIdlingRecource;
import vkurman.bakingapp.ui.RecipeActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.util.Log;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;

/**
 * RecipeSelectionInRecycleViewTest - testing selection of recipe in the recycleview
 * Created by Vassili Kurman on 02/06/2018.
 * Version 1.0
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeSelectionInRecycleViewTest {

    private static final String RECIPE_NAME = "Nutella Pie";

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityRule = new ActivityTestRule<>(RecipesActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = RecipeCountingIdlingRecource.getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void recyclerViewIsDisplayed()  {
        onView(withId(R.id.rv_recipes)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnFirstRecipe() {
        // Open the first recipe
        Espresso.onView(withId(R.id.rv_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Return
        Espresso.pressBack();
    }

    @After
    public void unregisterIdlingRecource() {
        if(mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}