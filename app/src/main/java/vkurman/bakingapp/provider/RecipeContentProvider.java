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

package vkurman.bakingapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * RecipeContentProvider. Content provider for recipes.
 * Created by Vassili Kurman on 07/04/2018.
 * Version 1.0
 */
public class RecipeContentProvider extends ContentProvider {

    private static final String TAG = RecipeContentProvider.class.getName();

    // Define final integer constants for the directory of recipes and a single item.
    // It's convention to use 100, 200, 300, etc for directories,
    // and related ints (101, 102, ..) for items in that directory.
    public static final int RECIPES = 100;
    public static final int INGREDIENTS = 200;
    public static final int STEPS = 300;
    public static final int RECIPE_WITH_ID = 101;
    public static final int INGREDIENTS_WITH_ID = 201;
    public static final int STEPS_WITH_ID = 301;

    // Declaring a static variable for the Uri matcher that you construct
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    // Member variable for a PlantDbHelper that's initialized in the onCreate() method
    private RecipeDbHelper mRecipeDbHelper;

    // Define a static buildUriMatcher method that associates URI's with their int match
    public static UriMatcher buildUriMatcher() {
        // Initialize a UriMatcher
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // Add URI matches

        uriMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.PATH_RECIPES, RECIPES);
        uriMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.PATH_RECIPES + "/#", RECIPE_WITH_ID);
        uriMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.PATH_INGREDIENTS, INGREDIENTS);
        uriMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.PATH_INGREDIENTS + "/#", INGREDIENTS_WITH_ID);
        uriMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.PATH_STEPS, STEPS);
        uriMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.PATH_STEPS + "/#", STEPS_WITH_ID);

        return uriMatcher;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();

        // Write URI matching code to identify the match for the recipes directory
        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned
        long id;
        switch (match) {
            case RECIPES:
                // Insert new values into the database
                id = db.insert(RecipeContract.RecipeEntry.TABLE_NAME_RECIPES, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(RecipeContract.RecipeEntry.CONTENT_URI_RECIPES, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case INGREDIENTS:
                // Insert new values into the database
                id = db.insert(RecipeContract.IngredientsEntry.TABLE_NAME_INGREDIENTS, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(RecipeContract.IngredientsEntry.CONTENT_URI_INGREDIENTS, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case STEPS:
                // Insert new values into the database
                id = db.insert(RecipeContract.StepsEntry.TABLE_NAME_STEPS, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(RecipeContract.StepsEntry.CONTENT_URI_STEPS, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mRecipeDbHelper = new RecipeDbHelper(context);
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Get access to underlying database (read-only for query)
        final SQLiteDatabase db = mRecipeDbHelper.getReadableDatabase();

        // Write URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);
        Cursor retCursor;
        String id;
        switch (match) {
            // Query for the recipes directory
            case RECIPES:
                Log.d(TAG, "Retrieving all recipes");
                retCursor = db.query(RecipeContract.RecipeEntry.TABLE_NAME_RECIPES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case RECIPE_WITH_ID:
                id = uri.getPathSegments().get(1);
                Log.d(TAG, "Retrieving recipe: " + id);
                retCursor = db.query(RecipeContract.RecipeEntry.TABLE_NAME_RECIPES,
                        projection,
                        "id=?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder);
                break;
            // Query for the ingredients directory
            case INGREDIENTS:
                Log.d(TAG, "Retrieving all ingredients");
                retCursor = db.query(RecipeContract.IngredientsEntry.TABLE_NAME_INGREDIENTS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case INGREDIENTS_WITH_ID:
                id = uri.getPathSegments().get(1);
                Log.d(TAG, "Retrieving ingredients for recipe: " + id);
                retCursor = db.query(RecipeContract.IngredientsEntry.TABLE_NAME_INGREDIENTS,
                        projection,
                        RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_PARENT_ID + "=?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder);
                break;
            // Query for the recipes directory
            case STEPS:
                Log.d(TAG, "Retrieving all steps");
                retCursor = db.query(RecipeContract.StepsEntry.TABLE_NAME_STEPS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case STEPS_WITH_ID:
                id = uri.getPathSegments().get(1);
                Log.d(TAG, "Retrieving steps for recipe: " + id);
                retCursor = db.query(RecipeContract.StepsEntry.TABLE_NAME_STEPS,
                        projection,
                        RecipeContract.StepsEntry.COLUMN_STEPS_PARENT_ID + "=?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}