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

package vkurman.bakingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vkurman.bakingapp.adapters.RecipesAdapter;
import vkurman.bakingapp.loaders.RecipesLoader;
import vkurman.bakingapp.models.Recipe;
import vkurman.bakingapp.ui.RecipeActivity;

public class RecipesActivity extends AppCompatActivity implements
        RecipesAdapter.RecipeClickListener, LoaderManager.LoaderCallbacks<List<Recipe>> {

    @BindView(R.id.rv_recipes) RecyclerView mRecyclerView;

    private RecipesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        // Binding views
        ButterKnife.bind(this);

        // use a grid layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecipesAdapter(this, null, this);
        mRecyclerView.setAdapter(mAdapter);

        // Setting loaders
        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
        return new RecipesLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> data) {
        if(data == null) {
            return;
        }

        if(mAdapter == null) {
            mAdapter = new RecipesAdapter(this, data, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.updateData(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {}

    @Override
    public void onRecipeClicked(Recipe recipe) {
        if(recipe != null) {
            Intent intent = new Intent(RecipesActivity.this, RecipeActivity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Recipe not set!", Toast.LENGTH_SHORT).show();
        }
    }
}