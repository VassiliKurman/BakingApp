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

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import vkurman.bakingapp.R;
import vkurman.bakingapp.models.Recipe;
import vkurman.bakingapp.models.Step;
import vkurman.bakingapp.utils.BakingAppConstants;
import vkurman.bakingapp.utils.RecipeUtils;

/**
 * RecipeActivity displays recipe ingredients and steps. In case of two pane
 * layout in addition to master layout it displays selected step details as well.
 */
public class RecipeActivity extends AppCompatActivity implements MasterListFragment.OnItemSelectedListener {

    private static final String TAG = "RecipeActivity";
    private static final String STEP_ID = "stepId";
    private static final String TWO_PANE = "twoPane";
    private static final String RECIPE = "recipe";
    // Variables to store the values for recipe
    private Recipe mRecipe;
    private int mCurrentStep;

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(RECIPE);
            mCurrentStep = savedInstanceState.getInt(STEP_ID, 0);
            mTwoPane = savedInstanceState.getBoolean(TWO_PANE);
            // Setting recipe to MasterListFragment
            setRecipeToMasterFragment();
        } else {
            Intent intent = getIntent();
            if (intent == null) {
                closeOnError();
            }
            mRecipe = intent.getParcelableExtra("recipe");
            if (mRecipe != null) {
                // Setting recipe to MasterListFragment
                setRecipeToMasterFragment();

                // Determine if you're creating a two-pane or single-pane display
                if (findViewById(R.id.recipe_details_container) != null) {
                    // This LinearLayout will only initially exist in the two-pane tablet case
                    mTwoPane = true;
                    final FragmentManager fragmentManager = getSupportFragmentManager();
                    final Step step = mRecipe.getSteps()[mCurrentStep];
                    if (step.getVideoURL() != null) {
                        MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
                        mediaPlayerFragment.setVideoUrl(step.getVideoURL());
                        fragmentManager.beginTransaction()
                                .add(R.id.media_container, mediaPlayerFragment)
                                .commit();
                    } else if (step.getThumbnailURL() != null) {
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
                    // We're in single-pane mode and displaying fragments on a phone in separate activities
                    mTwoPane = false;
                }
            }
        }
    }

    /**
     * Loading MasterListFragment into activity using FragmentManager
     */
    private void setRecipeToMasterFragment() {
        // Setting title for title bar
        getSupportActionBar().setTitle(mRecipe.getName());
        // Getting reference to FragmentManager
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final MasterListFragment fragment = (MasterListFragment) fragmentManager.findFragmentById(R.id.master_list_fragment);
        if (fragment == null) {
            Log.e(TAG, "Can't get ref to MasterListFragment");
            return;
        } else {
            fragment.setRecipe(mRecipe);
        }
    }

    // Define the behavior for onItemSelected
    @Override
    public void onItemSelected(int position) {
        if(position > 0 && position <= mRecipe.getSteps().length) {
            // Handle the two-pane case and replace existing fragments right when a new step is selected from the master list
            mCurrentStep = position - 1;
            if (mTwoPane) {
                // Create two=pane interaction
                // Add the fragment to its container using a FragmentManager and a Transaction
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                final Step step = mRecipe.getSteps()[mCurrentStep];
                // Remove media fragment
                if(fragmentManager.findFragmentById(R.id.media_container) != null) {
                    fragmentTransaction.remove(fragmentManager.findFragmentById(R.id.media_container));
                }
                if (step.getVideoURL() != null) {
                    MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
                    mediaPlayerFragment.setVideoUrl(step.getVideoURL());
                    fragmentManager.beginTransaction()
                            .add(R.id.media_container, mediaPlayerFragment)
                            .commit();
                } else if (step.getThumbnailURL() != null) {
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
                // Handle the single-pane phone case by passing information in a Bundle attached to an Intent
                // Put this information in a Bundle and attach it to an Intent that will launch an Activity
                Bundle b = new Bundle();
                b.putInt(BakingAppConstants.INTENT_NAME_FOR_STEP_ID, mRecipe.getSteps()[mCurrentStep].getId());
                Log.d(TAG, "Step passed to RecipeDetailsActivity");
                b.putParcelable("recipe", mRecipe);
                // Attach the Bundle to an intent
                final Intent intent = new Intent(this, RecipeDetailsActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_show_widget:
                RecipeUtils.updateWidgetData(this, mRecipe);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_ID, mCurrentStep);
        outState.putParcelable(RECIPE, mRecipe);
        outState.putBoolean(TWO_PANE, mTwoPane);
    }

    /**
     * Getter for Recipe.
     *
     * @return Recipe - recipe set to this activity.
     */
    public Recipe getRecipe() {
        return mRecipe;
    }

    /**
     * Closes and displays message when error occurs
     */
    private void closeOnError() {
        finish();
        Toast.makeText(this, "Error when retrieving intent", Toast.LENGTH_SHORT).show();
    }
}