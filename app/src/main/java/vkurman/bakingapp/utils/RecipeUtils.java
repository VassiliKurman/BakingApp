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
package vkurman.bakingapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import vkurman.bakingapp.R;
import vkurman.bakingapp.models.Ingredient;
import vkurman.bakingapp.models.Recipe;
import vkurman.bakingapp.models.Step;
import vkurman.bakingapp.provider.RecipeContract;
import vkurman.bakingapp.provider.RecipeDbHelper;

/**
 * RecipeUtils class is a helper class that handles tasks such as creating URL and getting response
 * from the web.
 * Created by Vassili Kurman on 07/04/2018.
 * Version 1.0
 */
public class RecipeUtils {
    /**
     * Tag for logging
     */
    private static final String TAG = "RecipeUtils";
    /**
     * URL where recipes are retrived from
     */
    private static final String API_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    /**
     * Build and returns URL for recipes.
     *
     * @return URL
     */
    public static final URL createRecipesUrl() {
        try {
            return new URL(API_BASE_URL);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error building URL!");
        }

        return null;
    }

    /**
     * Returns response from specified URL in json format.
     *
     * @param url - url to specific query
     * @return String - response from server in json format
     * @throws IOException - throws when can't open url connection
     */
    public static final String getJsonResponseFromWeb(URL url) throws IOException {
        if(url != null) {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                if(scanner.hasNext()) {
                    return scanner.next();
                }
            } finally {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    /**
     * Saves recipes and ingredients into database
     *
     * @param context
     * @param recipes
     */
    public static void saveRecipes(Context context, List<Recipe> recipes) {
        // Create new empty ContentValues object
        ContentValues recipeContentValues = new ContentValues();
        // Put the task description and selected mPriority into the ContentValues
        for (Recipe recipe: recipes) {
            recipeContentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPES_ID, recipe.getId());
            recipeContentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPES_NAME, recipe.getName());
            recipeContentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPES_SERVINGS, recipe.getServings());
            recipeContentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPES_IMAGE, recipe.getImage());
            // Insert the content values via a ContentResolver
            Uri recipeUri = context.getContentResolver().insert(RecipeContract.RecipeEntry.CONTENT_URI_RECIPES, recipeContentValues);

            // Log display
            if (recipeUri != null) {
                Log.d(TAG, recipeUri.toString());
            }

            ContentValues ingredientContentValues = new ContentValues();
            for(Ingredient ingredient: recipe.getIngredients()) {
                ingredientContentValues.put(RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_PARENT_ID, recipe.getId());
                ingredientContentValues.put(RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_QUANTITY, ingredient.getQuantity());
                ingredientContentValues.put(RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_MEASURE, ingredient.getMeasure());
                ingredientContentValues.put(RecipeContract.IngredientsEntry.COLUMN_INGREDIENTS_INGREDIENT, ingredient.getIngredient());
                // Insert the content values via a ContentResolver
                Uri ingredientUri = context.getContentResolver().insert(RecipeContract.IngredientsEntry.CONTENT_URI_INGREDIENTS, ingredientContentValues);
                if (ingredientUri != null) {
                    Log.d(TAG, ingredientUri.toString());
                }
            }

            ContentValues stepsContentValues = new ContentValues();
            for(Step step: recipe.getSteps()) {
                stepsContentValues.put(RecipeContract.StepsEntry.COLUMN_STEPS_ID, step.getId());
                stepsContentValues.put(RecipeContract.StepsEntry.COLUMN_STEPS_PARENT_ID, recipe.getId());
                stepsContentValues.put(RecipeContract.StepsEntry.COLUMN_STEPS_SHORT_DESCRIPTION, step.getShortDescription());
                stepsContentValues.put(RecipeContract.StepsEntry.COLUMN_STEPS_DESCRIPTION, step.getDescription());
                stepsContentValues.put(RecipeContract.StepsEntry.COLUMN_STEPS_VIDEO_URL, step.getVideoURL());
                stepsContentValues.put(RecipeContract.StepsEntry.COLUMN_STEPS_THUMBNAIL_URL, step.getThumbnailURL());
                // Insert the content values via a ContentResolver
                Uri stepUri = context.getContentResolver().insert(RecipeContract.StepsEntry.CONTENT_URI_STEPS, stepsContentValues);
                if (stepUri != null) {
                    Log.d(TAG, stepUri.toString());
                }
            }
        }
    }

    /**
     * Check if the database exist and can be read.
     * Code snippet taken from:
     * <code>https://stackoverflow.com/questions/3386667/query-if-android-database-exists?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa</code>
     *
     * @return true if it exists and can be read, false if it doesn't
     */
    public static boolean isDbExists(Context context) {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(context.getDatabasePath(RecipeDbHelper.DATABASE_NAME).toString(), null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }

    /**
     * Saves data to sSharedPreferences and notifies WidgetManager about update
     *
     * @param context - Context
     * @param recipe - Recipe
     */
    public static void updateWidgetData(Context context, Recipe recipe) {
        if(context == null || recipe == null) {
            Log.e(TAG, "Provided parameter is NULL: " + context == null ? "context" : "recipe");
            return;
        }
        updateWidgetData(context, recipe.getId(), recipe.getName());
    }

    /**
     * Saves data to sSharedPreferences and notifies WidgetManager about update
     *
     * @param context - Context
     * @param recipeId - id for recipe
     * @param recipeName - name of recipe
     */
    public static void updateWidgetData(Context context, int recipeId, String recipeName) {
        if(context == null || recipeId < 0 || recipeName == null) {
            Log.e(TAG, "Provided parameter is NOT valid: " + (recipeId < 0 ? "recipeId" : context == null ? "context" : "recipeName"));
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.saved_recipe_id_key), recipeId);
        editor.putString(context.getString(R.string.saved_recipe_name_key), recipeName);
        editor.apply();
        // Notifying AppWidgetManager about data change
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
//        int[] ids = appWidgetManager.getAppWidgetIds(?);
//        appWidgetManager.notifyAppWidgetViewDataChanged(ids, ?);
    }
}