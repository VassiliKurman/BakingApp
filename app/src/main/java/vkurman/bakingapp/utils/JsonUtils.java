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
 * JsonUtils
 * Created by Vassili Kurman on 07/04/2018.
 * Version 1.0
 */

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    // Ingredient Json names
    private static final String JSON_RECIPES = "recipes";
    private static final String JSON_RECIPE_ID = "id";
    private static final String JSON_RECIPE_NAME = "name";
    private static final String JSON_RECIPE_INGREDIENTS = "ingredients";
    private static final String JSON_RECIPE_STEPS = "steps";
    private static final String JSON_RECIPE_SERVINGS = "servings";
    private static final String JSON_RECIPE_IMAGE = "image";

    // Ingredient Json names
    private static final String JSON_STEPS = "steps";
    private static final String JSON_STEP_ID = "id";
    private static final String JSON_STEP_SHORT_DESCRIPTION = "shortDescription";
    private static final String JSON_STEP_DESCRIPTION = "description";
    private static final String JSON_STEP_VIDEO_URL = "videoURL";
    private static final String JSON_STEP_THUMBNAIL_URL = "thumbnailURL";

    // Ingredient Json names
    private static final String JSON_INGREDIENTS = "ingredients";
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

        try {
            // Parsing json string to json object
            JSONObject jsonObject = new JSONObject(json);
            // Getting json array results from json object
            JSONArray resultsArray = jsonObject.optJSONArray(JSON_RECIPES);
            if(resultsArray.length() > 0) {
                Log.d(TAG, "Objects in json results array: " + resultsArray.length());
                for(int i = 0; i < resultsArray.length(); i++) {
                    JSONObject movieJsonObject = resultsArray.optJSONObject(i);
                    // Getting individual values from json object
                    int id = movieJsonObject.optInt(JSON_RECIPE_ID);
                    String name = movieJsonObject.optString(JSON_RECIPE_NAME);
                    int servings = movieJsonObject.optInt(JSON_RECIPE_SERVINGS);
                    String image = movieJsonObject.optString(JSON_RECIPE_IMAGE);

                    // TODO retrieve data bellow
                    Ingredient[] ingredients = parseIngredientJson(JSON_RECIPE_INGREDIENTS);
                    // TODO retrieve data bellow
                    Step[] steps = parseStepJson(JSON_RECIPE_STEPS);

                    recipes.add(new Recipe(id, name, ingredients, steps, servings, image));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parse Recipes Json: " + e);
        }

        return recipes;
    }

    /**
     * Fetches and returns list of Ingredients from json string.
     *
     * @param json - string in json format
     * @return Ingredient[]
     */
    public static Ingredient[] parseIngredientJson(String json) {

        final List<Ingredient> ingredients = new ArrayList<>();

        try {
            // Parsing json string to json object
            JSONObject jsonObject = new JSONObject(json);
            // Getting json array results from json object
            JSONArray resultsArray = jsonObject.optJSONArray(JSON_INGREDIENTS);
            if(resultsArray.length() > 0) {
                for(int i = 0; i < resultsArray.length(); i++) {
                    JSONObject ingredientsJsonObject = resultsArray.optJSONObject(i);
                    // Getting individual values from json object
                    int quantity = ingredientsJsonObject.optInt(JSON_INGREDIENT_QUANTITY);
                    String measure = ingredientsJsonObject.optString(JSON_INGREDIENT_MEASURE);
                    String ingredient = ingredientsJsonObject.optString(JSON_INGREDIENT_INGREDIENT);

                    ingredients.add(new Ingredient(quantity, measure, ingredient));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parse Ingredient Json: " + e);
        }

        // Converting to array
        Ingredient[] array = new Ingredient[ingredients.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = ingredients.get(i);
        }

        return array;
    }

    /**
     * Fetches and returns list of Steps from json string.
     *
     * @param json - string in json format
     * @return Step[]
     */
    public static Step[] parseStepJson(String json) {

        final List<Step> steps = new ArrayList<>();

        try {
            // Parsing json string to json object
            JSONObject jsonObject = new JSONObject(json);
            // Getting json array results from json object
            JSONArray resultsArray = jsonObject.optJSONArray(JSON_STEPS);
            if(resultsArray.length() > 0) {
                for(int i = 0; i < resultsArray.length(); i++) {
                    JSONObject ingredientsJsonObject = resultsArray.optJSONObject(i);
                    // Getting individual values from json object
                    int id = ingredientsJsonObject.optInt(JSON_STEP_ID);
                    String shortDescription = ingredientsJsonObject.optString(JSON_STEP_SHORT_DESCRIPTION);
                    String description = ingredientsJsonObject.optString(JSON_STEP_DESCRIPTION);
                    String videoURL = ingredientsJsonObject.optString(JSON_STEP_VIDEO_URL);
                    String thumbnailURL = ingredientsJsonObject.optString(JSON_STEP_THUMBNAIL_URL);

                    steps.add(new Step(id, shortDescription, description, videoURL, thumbnailURL));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parse Steps Json: " + e);
        }

        // Converting to array
        Step[] array = new Step[steps.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = steps.get(i);
        }

        return array;
    }
}
