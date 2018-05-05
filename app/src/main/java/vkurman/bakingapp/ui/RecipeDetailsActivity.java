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

package vkurman.bakingapp.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import vkurman.bakingapp.R;
import vkurman.bakingapp.models.Ingredient;
import vkurman.bakingapp.models.Recipe;
import vkurman.bakingapp.models.Step;

/**
 * RecipeDetailsActivity displays details about ingredients or selected step in recipe.
 */
public class RecipeDetailsActivity extends AppCompatActivity {

    private static final String TAG = "RecipeDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Step details");

        Step step = getIntent().getParcelableExtra("step");

        // Add the fragment to its container using a FragmentManager and a Transaction
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(step != null) {
            if (step.getVideoURL() != null && !step.getVideoURL().isEmpty()) {
                MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
                mediaPlayerFragment.setVideoUrl(step.getVideoURL());
                fragmentManager.beginTransaction()
                        .add(R.id.media_container, mediaPlayerFragment)
                        .commit();
            } else if (step.getThumbnailURL() != null && !step.getThumbnailURL().isEmpty()) {
                ThumbnailFragment thumbnailFragment = new ThumbnailFragment();
                thumbnailFragment.setThumbnailUrl(step.getThumbnailURL());
                fragmentManager.beginTransaction()
                        .add(R.id.media_container, thumbnailFragment)
                        .commit();
            }

            StepInstructionsFragment stepFragment = new StepInstructionsFragment();
            stepFragment.setStep(step);
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_step_container, stepFragment)
                    .commit();
        } else {
//            Ingredient[] ingredients = getIntent().getParcelableArrayExtra("ingredients");

            Recipe recipe = getIntent().getParcelableExtra("recipe");
            if(recipe == null) {
                Log.e(TAG, "Recipe not passed!");
                return;
            }
            Ingredient[] ingredients = recipe.getIngredients();
            if (ingredients != null) {
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setIngredients(ingredients);
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_step_container, ingredientsFragment)
                        .commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}