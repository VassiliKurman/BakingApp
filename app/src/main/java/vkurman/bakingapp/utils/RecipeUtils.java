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
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import vkurman.bakingapp.models.Ingredient;
import vkurman.bakingapp.models.Recipe;
import vkurman.bakingapp.models.Step;
import vkurman.bakingapp.provider.RecipeContract;

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
                Uri stepUri = context.getContentResolver().insert(RecipeContract.IngredientsEntry.CONTENT_URI_INGREDIENTS, stepsContentValues);
                if (stepUri != null) {
                    Log.d(TAG, stepUri.toString());
                }
            }
        }
    }
}