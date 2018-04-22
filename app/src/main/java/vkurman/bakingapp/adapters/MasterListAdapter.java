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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import vkurman.bakingapp.R;
import vkurman.bakingapp.models.Step;

/**
 * MasterListAdapter
 * Created by Vassili Kurman on 20/04/2018.
 * Version 1.0
 */

public class MasterListAdapter extends BaseAdapter {
    // Keeps track of the context and list of steps to display
    private Context mContext;
    private Step[] mSteps;

    /**
     * Constructor method
     *
     * @param context - app context
     * @param steps - steps for recipe
     */
    public MasterListAdapter(Context context, Step[] steps) {
        mContext = context;
        if (steps != null) {
            mSteps = steps;
        }
    }

    /**
     * Returns the number of items the adapter will display
     */
    @Override
    public int getCount() {
        return mSteps.length;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_step_layout, parent, false);
        }

        final Step step = mSteps[position];

        // Get TextView's for ingredient
        ImageView imageRecipe = convertView.findViewById(R.id.iv_list_step_image);
        TextView textRecipe = convertView.findViewById(R.id.tv_list_step_text);

        // Set the text for TextView
        final String text = step.getId() + " " + step.getShortDescription();
        textRecipe.setText(text);
        Picasso.with(convertView.getContext())
                .load(mSteps[position].getThumbnailURL())
                .error(R.drawable.error_image)
                .placeholder(R.drawable.placeholder_image)
                .into(imageRecipe);

        return convertView;
    }
}