package vkurman.bakingapp.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vkurman.bakingapp.models.Ingredient;
import vkurman.bakingapp.models.Recipe;
import vkurman.bakingapp.models.Step;

/**
 * JsonUtils parses json formatted string into specific object.
 *
 * Created by Vassili Kurman on 07/04/2018.
 * Version 1.0
 */

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    // Ingredient Json names
    private static final String JSON_RECIPE_ID = "id";
    private static final String JSON_RECIPE_NAME = "name";
    private static final String JSON_RECIPE_INGREDIENTS = "ingredients";
    private static final String JSON_RECIPE_STEPS = "steps";
    private static final String JSON_RECIPE_SERVINGS = "servings";
    private static final String JSON_RECIPE_IMAGE = "image";

    // Ingredient Json names
    private static final String JSON_STEP_ID = "id";
    private static final String JSON_STEP_SHORT_DESCRIPTION = "shortDescription";
    private static final String JSON_STEP_DESCRIPTION = "description";
    private static final String JSON_STEP_VIDEO_URL = "videoURL";
    private static final String JSON_STEP_THUMBNAIL_URL = "thumbnailURL";

    // Ingredient Json names
    private static final String JSON_INGREDIENT_QUANTITY = "quantity";
    private static final String JSON_INGREDIENT_MEASURE = "measure";
    private static final String JSON_INGREDIENT_INGREDIENT = "ingredient";

    /**
     * Fetches and returns list of recipes from json string.
     *
     * @param json - string in json format
     * @return List<Recipe>
     */
    public static List<Recipe> parseRecipeJson(String json) {

        final List<Recipe> recipes = new ArrayList<>();

        if (json == null || json.isEmpty()) {
            return recipes;
        }

        try {
            // Parsing json string to json object
            JSONArray recipesArray = new JSONArray(json);

            Log.d(TAG, "Objects in json: " + recipesArray.length());
            // Getting json array results from json object
            if(recipesArray.length() > 0) {
                for(int i = 0; i < recipesArray.length(); i++) {
                    JSONObject recipeJsonObject = recipesArray.optJSONObject(i);
                    // Getting individual values from json object
                    int id = recipeJsonObject.optInt(JSON_RECIPE_ID);
                    String name = recipeJsonObject.optString(JSON_RECIPE_NAME);
                    int servings = recipeJsonObject.optInt(JSON_RECIPE_SERVINGS);
                    String image = recipeJsonObject.optString(JSON_RECIPE_IMAGE);

                    // Retrieving ingredients
                    Ingredient[] ingredients = parseIngredientJson(recipeJsonObject.optJSONArray(JSON_RECIPE_INGREDIENTS));
                    // Retrieving steps
                    Step[] steps = parseStepJson(recipeJsonObject.optJSONArray(JSON_RECIPE_STEPS));

                    recipes.add(new Recipe(id, name, ingredients, steps, servings, image));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parse Recipes Json: " + e);
        }

        for(Recipe recipe : recipes) {
            Log.d(TAG, "Recipe: " + recipe.getName());
            for(Ingredient ingredient : recipe.getIngredients()) {
                Log.d(TAG, "-----Ingredient: " + ingredient.getIngredient());
            }
            for(Step step : recipe.getSteps()) {
                Log.d(TAG, "-----Step: " + step.getId() + " " + step.getShortDescription());
            }
        }

        return recipes;
    }

    /**
     * Fetches and returns list of Ingredients from json string.
     *
     * @param json - string in json format
     * @return Ingredient[]
     */
    public static Ingredient[] parseIngredientJson(JSONArray json) {
         if(json.length() > 0) {
             final Ingredient[] ingredients = new Ingredient[json.length()];
             for (int i = 0; i < json.length(); i++) {
                 JSONObject ingredientsJsonObject = json.optJSONObject(i);
                 // Getting individual values from json object
                 String quantity = ingredientsJsonObject.optString(JSON_INGREDIENT_QUANTITY);
                 String measure = ingredientsJsonObject.optString(JSON_INGREDIENT_MEASURE);
                 String ingredient = ingredientsJsonObject.optString(JSON_INGREDIENT_INGREDIENT);

                 ingredients[i] = new Ingredient(quantity, measure, ingredient);
             }
             Log.d(TAG, "Created ingredients: " + ingredients.length);
             return ingredients;
         }
         return null;
    }

    /**
     * Fetches and returns list of Steps from json string.
     *
     * @param json - string in json format
     * @return Step[]
     */
    public static Step[] parseStepJson(JSONArray json) {
        if(json.length() > 0) {
            Step[] steps = new Step[json.length()];
            for(int i = 0; i < json.length(); i++) {
                JSONObject ingredientsJsonObject = json.optJSONObject(i);
                // Getting individual values from json object
                int id = ingredientsJsonObject.optInt(JSON_STEP_ID);
                String shortDescription = ingredientsJsonObject.optString(JSON_STEP_SHORT_DESCRIPTION);
                String description = ingredientsJsonObject.optString(JSON_STEP_DESCRIPTION);
                String videoURL = ingredientsJsonObject.optString(JSON_STEP_VIDEO_URL);
                String thumbnailURL = ingredientsJsonObject.optString(JSON_STEP_THUMBNAIL_URL);

                steps[i] = new Step(id, shortDescription, description, videoURL, thumbnailURL);
            }
            Log.d(TAG, "Created steps: " + steps.length);
            return steps;
        }
        return null;
    }
}
