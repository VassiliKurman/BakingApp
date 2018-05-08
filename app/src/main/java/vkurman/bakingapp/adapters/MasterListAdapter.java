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

package vkurman.bakingapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import vkurman.bakingapp.R;
import vkurman.bakingapp.models.Ingredient;
import vkurman.bakingapp.models.Recipe;
import vkurman.bakingapp.models.Step;

/**
 * MasterListAdapter
 * Created by Vassili Kurman on 20/04/2018.
 * Version 1.0
 */
public class MasterListAdapter extends BaseAdapter {

    private static final String TAG = "MasterListAdapter";

    // Keeps track of the context and recipe to display
    private Context mContext;
    private Recipe mRecipe;

    /**
     * Constructor method
     *
     * @param context - app context
     * @param recipe - recipe
     */
    public MasterListAdapter(Context context, Recipe recipe) {
        mContext = context;
        mRecipe = recipe;
    }

    /**
     * Returns the number of items the adapter will display
     */
    @Override
    public int getCount() {
        return (mRecipe == null || mRecipe.getSteps() == null) ? 0 : mRecipe.getSteps().length + 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Creates a new TextView for each item referenced by the adapter
     */
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_ingredients, parent, false);
            Log.d(TAG, "Ingredients");

            Ingredient[] ingredients = mRecipe.getIngredients();
            if(ingredients != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < ingredients.length; i++) {
                    Ingredient ingredient = ingredients[i];
                    String ing = String.format("- %s %d%s",
                            ingredient.getIngredient(),
                            ingredient.getQuantity(),
                            ingredient.getMeasure());
                    sb.append(ing);
                    if (i != ingredients.length - 1) {
                        sb.append("\n");
                    }
                }
                TextView ingredientsTextView = convertView.findViewById(R.id.tv_ingredients);
                ingredientsTextView.setText(sb.toString());
            }
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_step_layout, parent, false);
            final Step step = mRecipe.getSteps()[position - 1];

            Log.d(TAG, "Step: " + step.getShortDescription());

            // Get TextView's for ingredient
            ImageView imageRecipe = convertView.findViewById(R.id.iv_list_step_image);
            TextView textRecipe = convertView.findViewById(R.id.tv_list_step_text);

            // Set the text for TextView
            textRecipe.setText(step.getShortDescription());
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(Integer.toString(step.getId()), R.color.colorPrimary);
            imageRecipe.setImageDrawable(drawable);
        }
        return convertView;
    }

    /**
     * Setting new array of steps.
     *
     * @param recipe - recipe
     */
    public void setRecipe(Recipe recipe) {
        mRecipe = recipe;
        notifyDataSetChanged();
    }
}