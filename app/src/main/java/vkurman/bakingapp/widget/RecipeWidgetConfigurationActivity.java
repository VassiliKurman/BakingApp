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

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vkurman.bakingapp.R;
import vkurman.bakingapp.provider.RecipeContract;

/**
 * Configuration activity for ingredients widget
 */
public class RecipeWidgetConfigurationActivity extends AppCompatActivity implements
        RecipeWidgetConfigurationAdapter.RecipeWidgetConfigurationClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * A unique identifier for database loader
     */
    private static final int DATABASE_LOADER_ID = 105;
    /**
     * A unique identifier for widget
     */
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    /**
     * RecycleView
     */
    @BindView(R.id.rv_widget_configuration)
    RecyclerView mRecyclerView;
    /**
     * RecycleView adapter
     */
    private RecipeWidgetConfigurationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_widget_configuration);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_CANCELED, resultValue);

        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecipeWidgetConfigurationAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(DATABASE_LOADER_ID, null, this);
    }

    @Override
    public void onRecipeWidgetConfigurationClicked(int id, String name) {
        // getting an instance of the AppWidgetManager
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RecipeWidgetProvider.updateAppWidget(this, appWidgetManager, mAppWidgetId, id, name);

        // creating the return Intent, set it with the Activity result, and finishing the Activity
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id) {
            case DATABASE_LOADER_ID:
                // If the loader id matches database loader, return a cursor loader
                return new CursorLoader(
                        getApplicationContext(),
                        RecipeContract.RecipeEntry.CONTENT_URI_RECIPES,
                        new String[]{
                            RecipeContract.RecipeEntry.COLUMN_RECIPES_ID,
                            RecipeContract.RecipeEntry.COLUMN_RECIPES_NAME},
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}