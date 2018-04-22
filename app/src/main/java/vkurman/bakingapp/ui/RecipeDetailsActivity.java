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

import vkurman.bakingapp.R;
import vkurman.bakingapp.models.Ingredient;
import vkurman.bakingapp.models.Step;

public class RecipeDetailsActivity extends AppCompatActivity {

    private Step step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        step = getIntent().getParcelableExtra("step");

        // Add the fragment to its container using a FragmentManager and a Transaction
        FragmentManager fragmentManager = getSupportFragmentManager();

//        if(step.getVideoURL() != null) {
//            MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
//            mediaPlayerFragment.setVideoUrl(step.getVideoURL());
//            fragmentManager.beginTransaction()
//                    .add(R.id.video_container, mediaPlayerFragment)
//                    .commit();
//        } else if(step.getThumbnailURL() != null) {
//            ThumbnailFragment thumbnailFragment = new ThumbnailFragment();
//            thumbnailFragment.setThumbnailUrl(step.getThumbnailURL());
//            fragmentManager.beginTransaction()
//                    .add(R.id.video_container, thumbnailFragment)
//                    .commit();
//        }

        StepInstructionsFragment stepFragment = new StepInstructionsFragment();
        stepFragment.setStep(step);
        fragmentManager.beginTransaction()
                .add(R.id.recipe_step_container, stepFragment)
                .commit();
    }
}