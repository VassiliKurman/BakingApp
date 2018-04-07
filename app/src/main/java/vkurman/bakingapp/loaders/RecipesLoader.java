package vkurman.bakingapp.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;
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

    private static final String TAG = "";

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
            return JsonUtils.parseRecipeJson(json);
        } catch (IOException e) {
            Log.e(TAG, "");
        }

        return null;
    }
}
