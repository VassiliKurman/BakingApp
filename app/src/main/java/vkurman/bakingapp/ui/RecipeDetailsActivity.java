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
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vkurman.bakingapp.R;
import vkurman.bakingapp.models.Recipe;
import vkurman.bakingapp.models.Step;
import vkurman.bakingapp.utils.BakingAppConstants;

/**
 * RecipeDetailsActivity displays details about ingredients or selected step in recipe.
 */
public class RecipeDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Reference to recipe
     */
    private Recipe mRecipe;
    /**
     * Reference to step id and value -1 for ingredients
     */
    private int mId;
    /**
     * View for step number
     */
    @BindView(R.id.tv_step_number)
    TextView mStepNumber;
    @BindView(R.id.btn_previous)
    Button mPreviousButton;
    @BindView(R.id.btn_next)
    Button mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_details_activity);
        // Binding views
        ButterKnife.bind(this);
        // Retrieving recipe and id
        mRecipe = getIntent().getParcelableExtra(BakingAppConstants.INTENT_NAME_FOR_RECIPE);
        mId = getIntent().getIntExtra(BakingAppConstants.INTENT_NAME_FOR_STEP_ID, -1);
        // Add the fragment to its container using a FragmentManager and a Transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(mRecipe != null && mId >= 0 && mId < mRecipe.getSteps().length) {
            Step step = mRecipe.getSteps()[mId];
            displayStep(fragmentManager, step);
        }
        // Setting onClickListeners
        mPreviousButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        if(mId == 0) {
            mPreviousButton.setVisibility(View.INVISIBLE);
        } else if (mId == mRecipe.getSteps().length - 1) {
            mNextButton.setVisibility(View.INVISIBLE);
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

    /**
     * Displays step fragments.
     *
     * @param fragmentManager
     * @param step
     */
    private void displayStep(FragmentManager fragmentManager, Step step) {
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
        // Displaying step instruction fragment
        StepInstructionsFragment stepFragment = new StepInstructionsFragment();
        stepFragment.setStep(step);
        fragmentManager.beginTransaction()
                .add(R.id.recipe_step_container, stepFragment)
                .commit();
    }

    // TODO check for correctness
    private void replaceStep(Step step) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (step.getVideoURL() != null && !step.getVideoURL().isEmpty()) {
            MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
            mediaPlayerFragment.setVideoUrl(step.getVideoURL());
            int mediaContainerID = R.id.media_container;
            fragmentTransaction.replace(mediaContainerID, mediaPlayerFragment);
        } else if (step.getThumbnailURL() != null && !step.getThumbnailURL().isEmpty()) {
            ThumbnailFragment thumbnailFragment = new ThumbnailFragment();
            thumbnailFragment.setThumbnailUrl(step.getThumbnailURL());
            int mediaContainerID = R.id.media_container;
            fragmentTransaction.replace(mediaContainerID, thumbnailFragment);
        }
        // Displaying step instruction fragment
        StepInstructionsFragment stepFragment = new StepInstructionsFragment();
        stepFragment.setStep(step);
        int stepContainerID = R.id.recipe_step_container;
        fragmentTransaction.replace(stepContainerID, stepFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        // TODO check loading device
        if(view == mPreviousButton) {
            if (mId > 0) {
                mId--;
                if(mId == 0) {
                    mPreviousButton.setVisibility(View.INVISIBLE);
                } else if(mId == mRecipe.getSteps().length - 2) {
                    mNextButton.setVisibility(View.VISIBLE);
                }
                mStepNumber.setText(String.valueOf(mId));
                replaceStep(mRecipe.getSteps()[mId]);
            }
        } else if (view == mNextButton) {
            if (mId < mRecipe.getSteps().length - 1) {
                mId++;
                if(mId == mRecipe.getSteps().length - 1) {
                    mNextButton.setVisibility(View.INVISIBLE);
                } else if(mId == 1) {
                    mPreviousButton.setVisibility(View.VISIBLE);
                }
                mStepNumber.setText(String.valueOf(mId));
                replaceStep(mRecipe.getSteps()[mId]);
            }
        }
    }
}