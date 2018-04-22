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
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vkurman.bakingapp.R;
import vkurman.bakingapp.models.Recipe;

/**
 * RecipesAdapter
 * Created by Vassili Kurman on 07/04/2018.
 * Version 1.0
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    /**
     * An on-click handler that allows for an Activity to interface with RecyclerView
     */
    final private RecipeClickListener mRecipeClickListener;
    /**
     * List of recipes
     */
    private List<Recipe> recipes;

    /**
     * An on-click handler that allows for an Activity to interface with RecyclerView
     */
    public interface RecipeClickListener {
        void onRecipeClicked(Recipe recipe);
    }

    /**
     * Constructor for RecipesAdapter
     *
     * @param recipes - list of recipes
     * @param recipeClickListener - item click listener
     */
    public RecipesAdapter(List<Recipe> recipes, RecipeClickListener recipeClickListener) {
        this.recipes = recipes;
        mRecipeClickListener = recipeClickListener;
    }

    /**
     * Provides a reference to the views for each data item.
     */
    class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        TextView mContent;

        RecipesViewHolder(View view) {
            super(view);

            mContent = view.findViewById(R.id.tv_list_recipe_text);
            view.setOnClickListener(this);
        }

        /**
         * Called whenever a user clicks on an item in the list.
         *
         * @param view - The View that was clicked
         */
        @Override
        public void onClick(View view) {
            if(recipes == null) {
                return;
            }
            int position = getAdapterPosition();
            if(position >= 0 && position < recipes.size()) {
                Recipe recipe = recipes.get(position);
                mRecipeClickListener.onRecipeClicked(recipe);
            }
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public RecipesAdapter.RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                               int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_recipe_layout;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new RecipesAdapter.RecipesViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        if(position >= 0 && position < recipes.size()) {
            final Recipe recipe = recipes.get(position);

            if(recipe == null) {
                return;
            }

            holder.mContent.setText(recipe.getName());
        }
    }

    // Return the size of list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }

    /**
     * Update data in Adapter.
     *
     * @param recipes - provided new list of recipes
     */
    public void updateData(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }
}