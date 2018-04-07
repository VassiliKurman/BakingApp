package vkurman.bakingapp.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import vkurman.bakingapp.models.Recipe;
import vkurman.bakingapp.utils.JsonUtils;
import vkurman.bakingapp.utils.RecipeUtils;

/**
 * RecipesLoader
 * Created by Vassili Kurman on 07/04/2018.
 * Version 1.0
 */

public class RecipesLoader extends AsyncTaskLoader<List<Recipe>> {

    private static final String TAG = "RecipesLoader";

    public RecipesLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    public List<Recipe> loadInBackground() {
        URL url = RecipeUtils.createRecipesUrl();

        if(url == null) {
            return null;
        }

        try {
            String json = RecipeUtils.getJsonResponseFromWeb(url);
            Log.d(TAG, "Recipes JSON length: " + json.length());
            return JsonUtils.parseRecipeJson(json);
        } catch (IOException e) {
            Log.e(TAG, "Exception getting response from web");
        }

        return null;
    }
}
