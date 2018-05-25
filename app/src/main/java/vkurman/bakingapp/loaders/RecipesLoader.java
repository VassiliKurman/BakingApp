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
 * RecipesLoader loads recipes from web source using RecipeUtils.
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
            // Saving data to database
            List<Recipe> recipes = JsonUtils.parseRecipeJson(json);
            if(!RecipeUtils.isDbExists(getContext())) {
                RecipeUtils.saveRecipes(getContext(), recipes);
            }
            return recipes;
        } catch (IOException e) {
            Log.e(TAG, "Exception getting response from web");
        }

        return null;
    }
}
