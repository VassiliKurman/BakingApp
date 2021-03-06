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

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * RecipeContract
 * Created by Vassili Kurman on 30/04/2018.
 * Version 1.0
 */
public class RecipeContract {
    /**
     * The authority, which is how your code knows which Content Provider to access
     */
    public static final String AUTHORITY = "vkurman.bakingapp";
    /**
     * The base content URI = "content://" + <authority>
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Defining the possible paths for accessing data in this contract
    /**
     * This is the path for the "recipes" directory
     */
    public static final String PATH_RECIPES = "recipes";
    /**
     * This is the path for the "steps" directory
     */
    public static final String PATH_STEPS = "steps";
    /**
     * This is the path for the "ingredients" directory
     */
    public static final String PATH_INGREDIENTS = "ingredients";

    public static final long INVALID_RECIPE_ID = -1;
    public static final long INVALID_STEP_ID = -1;
    public static final long INVALID_INGREDIENT_ID = -1;

    /**
     * Inner class for Recipe columns
     */
    public static final class RecipeEntry implements BaseColumns {
        // Entry content URI = base content URI + path
        public static final Uri CONTENT_URI_RECIPES =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();
        // Recipes table
        public static final String TABLE_NAME_RECIPES = "recipes";
        public static final String COLUMN_RECIPES_ID = "id";
        public static final String COLUMN_RECIPES_NAME = "name";
        public static final String COLUMN_RECIPES_SERVINGS = "servings";
        public static final String COLUMN_RECIPES_IMAGE = "image";
    }

    /**
     * Inner class for Steps columns
     */
    public static final class StepsEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI_STEPS =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STEPS).build();
        // Steps table
        public static final String TABLE_NAME_STEPS = "steps";
        public final static String _ID = BaseColumns._ID;
        public static final String COLUMN_STEPS_ID = "step_id";
        public static final String COLUMN_STEPS_PARENT_ID = "recipe_id";
        public static final String COLUMN_STEPS_SHORT_DESCRIPTION = "shortDescription";
        public static final String COLUMN_STEPS_DESCRIPTION = "description";
        public static final String COLUMN_STEPS_VIDEO_URL = "videoURL";
        public static final String COLUMN_STEPS_THUMBNAIL_URL = "thumbnailURL";
    }

    /**
     * Inner class for Ingredients columns
     */
    public static final class IngredientsEntry implements BaseColumns {
        // Entry content URI = base content URI + path
        public static final Uri CONTENT_URI_INGREDIENTS =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENTS).build();
        // Ingredients table
        public static final String TABLE_NAME_INGREDIENTS = "ingredients";
        public final static String _ID = BaseColumns._ID;
        public static final String COLUMN_INGREDIENTS_PARENT_ID = "recipe_id";
        public static final String COLUMN_INGREDIENTS_QUANTITY = "quantity";
        public static final String COLUMN_INGREDIENTS_MEASURE = "measure";
        public static final String COLUMN_INGREDIENTS_INGREDIENT = "ingredient";
    }
}