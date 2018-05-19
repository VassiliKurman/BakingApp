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
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.Objects;

import vkurman.bakingapp.R;
import vkurman.bakingapp.provider.RecipeContract;

/**
 * Service for recipe ingredients widget.
 */
public class RecipeWidgetService extends RemoteViewsService {

    private static final String TAG = RecipeWidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int id = Integer.valueOf(Objects.requireNonNull(intent.getData()).getSchemeSpecificPart());
        Log.d(TAG, "Received id: " + id);
        return new ListRemoteViewsFactory(getApplicationContext(), id);
    }

    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        final Context mContext;
        final int mRecipeId;
        Cursor mCursor;

        ListRemoteViewsFactory(Context context, int recipeId) {
            mContext = context;
            mRecipeId = recipeId;
        }

        @Override
        public void onCreate() {
            Uri uri = RecipeContract.IngredientsEntry.CONTENT_URI_INGREDIENTS.buildUpon()
                    .appendPath(String.valueOf(mRecipeId))
                    .build();
            Log.d(TAG, "Build Uri: " + uri);
            mCursor = mContext.getContentResolver().query(
                    uri,
                    null,
                    null,
                    null,
                    null);
        }

        @Override
        public void onDataSetChanged() {}

        @Override
        public void onDestroy() {
            if (mCursor != null)
                mCursor.close();
        }

        @Override
        public int getCount() {
            return mCursor == null ? 0 : mCursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (mCursor == null || mCursor.getCount() == 0) return null;

            mCursor.moveToPosition(position);
            int quantityColumnIndex = mCursor.getColumnIndex(RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_QUANTITY);
            int measureColumnIndex = mCursor.getColumnIndex(RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_MEASURE);
            int nameColumnIndex = mCursor.getColumnIndex(RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_INGREDIENT);

            String quantity = mCursor.getString(quantityColumnIndex);
            String measure = mCursor.getString(measureColumnIndex);
            String ingredientName = mCursor.getString(nameColumnIndex);

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.list_recipe_widget_layout);
            views.setTextViewText(R.id.tv_widget_quantity, quantity);
            views.setTextViewText(R.id.tv_widget_measure, measure);
            views.setTextViewText(R.id.tv_widget_ingredient, ingredientName);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}