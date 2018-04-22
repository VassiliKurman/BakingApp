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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import vkurman.bakingapp.R;
import vkurman.bakingapp.adapters.MasterListAdapter;
import vkurman.bakingapp.models.Recipe;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MasterListFragment.OnItemSelectedListener} interface
 * to handle interaction events.
 */
public class MasterListFragment extends Fragment {

    /**
     * Tag for Log
     */
    private static final String TAG = "MasterListFragment";
    /**
     * Recipe
     */
    private Recipe mRecipe;
    /**
     * Item click listener
     */
    private OnItemSelectedListener mListener;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnItemSelectedListener {
        // TODO: Update argument type and name
        void onItemSelected(int position);
    }

    public MasterListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, true);
        // Get a reference to the ListView in the master_list_view xml layout file
        ListView listView = rootView.findViewById(R.id.master_list_view);

        mRecipe = ((RecipeActivity) getActivity()).getRecipe();

        if(mRecipe != null) {
            // Create the adapter
            // This adapter takes in the context and Recipe
            MasterListAdapter mAdapter = new MasterListAdapter(getContext(), mRecipe.getSteps());

            // Set the adapter on the ListView
            listView.setAdapter(mAdapter);

            // Set a click listener on the listView and trigger the callback onItemSelected when an item is clicked
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Trigger the callback method and pass in the position that was clicked
                    mListener.onItemSelected(position);
                }
            });
        } else {
            Log.e(TAG, "Recipes not set");
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnItemSelectedListener) {
            mListener = (OnItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    /**
     * Sets recipe for this fragment.
     *
     * @param recipe
     */
    public void setRecipe(Recipe recipe) {
        mRecipe = recipe;
    }
}