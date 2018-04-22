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
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import vkurman.bakingapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThumbnailFragment extends Fragment {

    private String mThumbnailUrl;

    public ThumbnailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Load the saved state (the string thumbnailUrl) if there is one
        if(savedInstanceState != null) {
            mThumbnailUrl = savedInstanceState.getString("thumbnailUrl");
        }

        // Inflate the ThumbnailFragment fragment layout
        View rootView = inflater.inflate(R.layout.fragment_thumbnail, container, false);

        // Get a reference to the ImageView in the fragment layout
        final ImageView imageView = rootView.findViewById(R.id.iv_step_thumbnail);

        // Set the image resource to the list item at the stored index
        Picasso.with(this.getContext())
                .load(mThumbnailUrl)
                .error(R.drawable.error_image)
                .placeholder(R.drawable.placeholder_image)
                .into(imageView);

        // Return the rootView
        return rootView;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        mThumbnailUrl = thumbnailUrl;
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        currentState.putString("thumbnailUrl", mThumbnailUrl);
    }
}