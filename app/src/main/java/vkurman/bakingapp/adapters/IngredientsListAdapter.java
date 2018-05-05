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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import vkurman.bakingapp.R;
import vkurman.bakingapp.models.Ingredient;

/**
 * IngredientsListAdapter
 * Created by Vassili Kurman on 22/04/2018.
 * Version 1.0
 */
public class IngredientsListAdapter extends BaseAdapter {

    private Context mContext;
    private Ingredient[] mIngredients;

    public IngredientsListAdapter(Context context, Ingredient[] ingredients) {
        mContext = context;
        mIngredients = ingredients;
    }

    @Override
    public int getCount() {
        return mIngredients == null ? 0 : mIngredients.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_ingredient_layout, viewGroup, false);
        }

        final Ingredient ingredient = mIngredients[position];

        if(ingredient == null)
            return view;

        // Get TextView's for ingredient
        TextView textIngredient = view.findViewById(R.id.tv_list_ingredient_ingredient);
        TextView textQuantity = view.findViewById(R.id.tv_list_ingredient_quantity);
        TextView textMeasure = view.findViewById(R.id.tv_list_ingredient_measure);

        // Set the text for TextView's
        textIngredient.setText(ingredient.getIngredient());
        textQuantity.setText(String.valueOf(ingredient.getQuantity()));
        textMeasure.setText(ingredient.getMeasure());

        return view;
    }

    /**
     * Sets new ingredients for adapter.
     *
     * @param ingredients - array of ingredients
     */
    public void setIngredients(Ingredient[] ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }
}