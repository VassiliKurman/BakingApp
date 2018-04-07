package vkurman.bakingapp.adapters;

import android.content.Context;
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

    private List<Recipe> recipes;

    /**
     * Provides a reference to the views for each data item.
     */
    public static class RecipesViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mContent;

        public RecipesViewHolder(View view) {
            super(view);

            mContent = view.findViewById(R.id.tv_list_recipe_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecipesAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecipesAdapter.RecipesViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_recipe_layout;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new RecipesAdapter.RecipesViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
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
}