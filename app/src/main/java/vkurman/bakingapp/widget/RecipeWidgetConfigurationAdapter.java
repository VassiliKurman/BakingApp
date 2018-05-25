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
package vkurman.bakingapp.widget;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vkurman.bakingapp.R;
import vkurman.bakingapp.provider.RecipeContract;
import vkurman.bakingapp.utils.RecipeUtils;

/**
 * RecipeWidgetConfigurationAdapter adapter class for RecycleView
 * Created by Vassili Kurman on 19/05/2018.
 * Version 1.0
 */
public class RecipeWidgetConfigurationAdapter extends RecyclerView.Adapter<RecipeWidgetConfigurationAdapter.RecipeWidgetConfigurationViewHolder> {
    /**
     * Tag for logging
     */
    private static final String TAG = RecipeWidgetConfigurationAdapter.class.getSimpleName();
    /**
     * Context
     */
    final private Context mContext;
    /**
     * An on-click handler that allows for an Activity to interface with RecyclerView
     */
    final private RecipeWidgetConfigurationAdapter.RecipeWidgetConfigurationClickListener mRecipeClickListener;
    /**
     * Cursor for recipes
     */
    private Cursor mCursor;

    /**
     * An on-click handler that allows for an Activity to interface with RecyclerView
     */
    public interface RecipeWidgetConfigurationClickListener {
        void onRecipeWidgetConfigurationClicked(int id, String name);
    }

    /**
     * Constructor for RecipesAdapter
     *
     * @param context - context
     * @param recipeClickListener - item click listener
     */
    public RecipeWidgetConfigurationAdapter(Context context, RecipeWidgetConfigurationAdapter.RecipeWidgetConfigurationClickListener recipeClickListener) {
        mContext = context;
        mRecipeClickListener = recipeClickListener;
    }

    /**
     * Provides a reference to the views for each data item.
     */
    class RecipeWidgetConfigurationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_list_recipe_widget_recipe_name)
        TextView mRecipeName;

        RecipeWidgetConfigurationViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        /**
         * Called whenever a user clicks on an item in the list.
         *
         * @param view - The View that was clicked
         */
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            int idColumnIndex = mCursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPES_ID);
            int nameColumnIndex = mCursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPES_NAME);
            // Recipe details
            int recipeId = mCursor.getInt(idColumnIndex);
            String recipeName = mCursor.getString(nameColumnIndex);
            // Writing log message
            Log.d(TAG, "Recipe for widget selected: " + recipeId + " " + recipeName);
            // Writing details to shared preferences
            RecipeUtils.updateWidgetData(view.getContext(), recipeId, recipeName);
            // Sending data to click listener
            mRecipeClickListener.onRecipeWidgetConfigurationClicked(recipeId, recipeName);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public RecipeWidgetConfigurationAdapter.RecipeWidgetConfigurationViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                               int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_recipe_widget_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new RecipeWidgetConfigurationAdapter.RecipeWidgetConfigurationViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecipeWidgetConfigurationAdapter.RecipeWidgetConfigurationViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int nameColumnIndex = mCursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPES_NAME);
        holder.mRecipeName.setText(mCursor.getString(nameColumnIndex));
    }

    // Return the size of list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    // This method swaps the old recipe result with the newly loaded ones and notify the change
    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }
}
