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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vkurman.bakingapp.R;
import vkurman.bakingapp.models.Ingredient;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment {

    /**
     * Key for ingredients
     */
    private static final String INGREDIENTS_KEY = "ingredients";
    /**
     * Ingredients
     */
    private Ingredient[] mIngredientsArray;
    private String mIngredientsString;
    private Unbinder mUnbinder;
    @BindView(R.id.tv_ingredients)
    TextView mIngredientsTextView;

    /**
     * Empty constructor
     */
    public IngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        mUnbinder = ButterKnife.bind(this, rootView);

        if (savedInstanceState == null) {
            if(mIngredientsArray != null){
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mIngredientsArray.length; i++) {
                    Ingredient ingredient = mIngredientsArray[i];
                    String ing = String.format("%s %d%s",
                            ingredient.getIngredient(),
                            ingredient.getQuantity(),
                            ingredient.getMeasure());
                    sb.append(ing);
                    if(i != mIngredientsArray.length - 1) {
                        sb.append("\n");
                    }
                    mIngredientsString = sb.toString();
                }
            }

        } else {
            mIngredientsString = (savedInstanceState.getString(INGREDIENTS_KEY));
        }
        mIngredientsTextView.setText(mIngredientsString);
        // Return the root view
        return rootView;
    }

    /**
     * Ingredient[] ingredients.
     *
     * @param ingredients - provided ingredients
     */
    public void setIngredients(Ingredient[] ingredients) {
        mIngredientsArray = ingredients;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        currentState.putString(INGREDIENTS_KEY, mIngredientsString);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}