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
import android.widget.ListView;

import vkurman.bakingapp.R;
import vkurman.bakingapp.adapters.IngredientsListAdapter;
import vkurman.bakingapp.models.Ingredient;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment {

    private Ingredient[] mIngredients;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Load the saved state (the Parcelable ingredients) if there is one
//        if(savedInstanceState != null) {
//            mIngredients = savedInstanceState.getParcelable("ingredients");
//        }

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        // Get a reference to the ListView in the fragment_master_list xml layout file
        ListView listView = rootView.findViewById(R.id.ingredients_list_view);

        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL the image resources to display
        IngredientsListAdapter mAdapter = new IngredientsListAdapter(getContext(), mIngredients);

        // Set the adapter on the GridView
        listView.setAdapter(mAdapter);

        // Return the root view
        return rootView;
    }

    /**
     * Ingredient[] ingredients.
     *
     * @param ingredients - provided ingredients
     */
    public void setIngredients(Ingredient[] ingredients) {
        mIngredients = ingredients;
    }

//    /**
//     * Save the current state of this fragment
//     */
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle currentState) {
//        currentState.putParcelableArray("ingredients", mIngredients);
//    }
}