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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vkurman.bakingapp.R;
import vkurman.bakingapp.models.Step;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepInstructionsFragment extends Fragment {

    private Step mStep;

    public StepInstructionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Load the saved state (the Parcelable step) if there is one
        if(savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable("step");
        }

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_step_instructions, container, false);

        if(mStep != null) {
            TextView description = rootView.findViewById(R.id.tv_step_description);
            description.setText(mStep.getDescription());
        }

        return rootView;
    }

    /**
     * Sets step.
     *
     * @param step - provided step
     */
    public void setStep(Step step) {
        mStep = step;
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        currentState.putParcelable("step", mStep);
    }
}