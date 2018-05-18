package vkurman.bakingapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import vkurman.bakingapp.models.Ingredient;
import vkurman.bakingapp.models.Recipe;
import vkurman.bakingapp.provider.RecipeContract;

/**
 * RecipeUtils
 * Created by Vassili Kurman on 07/04/2018.
 * Version 1.0
 */

public class RecipeUtils {

    private static final String TAG = "RecipeUtils";

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
//        ContentValues recipeContentValues = new ContentValues();
        // Put the task description and selected mPriority into the ContentValues
        for (Recipe recipe: recipes) {
//            recipeContentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPES_ID, recipe.getId());
//            recipeContentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPES_NAME, recipe.getName());
//            recipeContentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPES_SERVINGS, recipe.getServings());
//            recipeContentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPES_IMAGE, recipe.getImage());
//            // Insert the content values via a ContentResolver
//            Uri uri = context.getContentResolver().insert(RecipeContract.RecipeEntry.CONTENT_URI_RECIPES, recipeContentValues);
//
//            // Log display
//            if (uri != null) {
//                Log.d(TAG, uri.toString());
//            }

            ContentValues ingredientContentValues = new ContentValues();
            for(Ingredient ingredient: recipe.getIngredients()) {
                ingredientContentValues.put(RecipeContract.RecipeEntry.COLUMN_INGREDIENTS_PARENT_ID, recipe.getId());
                ingredientContentValues.put(RecipeContract.RecipeEntry.COLUMN_INGREDIENTS_QUANTITY, ingredient.getQuantity());
                ingredientContentValues.put(RecipeContract.RecipeEntry.COLUMN_INGREDIENTS_MEASURE, ingredient.getMeasure());
                ingredientContentValues.put(RecipeContract.RecipeEntry.COLUMN_INGREDIENTS_INGREDIENT, ingredient.getIngredient());
                // Insert the content values via a ContentResolver
                Uri uri = context.getContentResolver().insert(RecipeContract.RecipeEntry.CONTENT_URI_INGREDIENTS, ingredientContentValues);
                if (uri != null) {
                Log.d(TAG, uri.toString());
            }
            }
        }
    }
}