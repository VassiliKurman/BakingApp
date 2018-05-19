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

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import vkurman.bakingapp.provider.RecipeContract.*;

/**
 * RecipeDbHelper
 * Created by Vassili Kurman on 30/04/2018.
 * Version 1.0
 */
public class RecipeDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "RecipeDbHelper";

    /**
     * The database name
     */
    private static final String DATABASE_NAME = "recipes.db";
    /**
     * If the database schema changes, than the database version needs to be incremented
     */
    private static final int DATABASE_VERSION = 1;

    // Constructor
    RecipeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold the recipe data
        final String SQL_CREATE_RECIPES_TABLE = "CREATE TABLE " + RecipeEntry.TABLE_NAME_RECIPES + " (" +
                RecipeEntry.COLUMN_RECIPES_ID + " INTEGER PRIMARY KEY," +
                RecipeEntry.COLUMN_RECIPES_NAME + " TEXT NOT NULL, " +
                RecipeEntry.COLUMN_RECIPES_SERVINGS + " INTEGER, " +
                RecipeEntry.COLUMN_RECIPES_IMAGE + " TEXT)";

        // Create a table to hold the steps data
        final String SQL_CREATE_STEPS_TABLE = "CREATE TABLE " + StepsEntry.TABLE_NAME_STEPS + " (" +
                StepsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                StepsEntry.COLUMN_STEPS_ID + " INTEGER NOT NULL," +
                StepsEntry.COLUMN_STEPS_PARENT_ID + " INTEGER NOT NULL, " +
                StepsEntry.COLUMN_STEPS_SHORT_DESCRIPTION + " TEXT NOT NULL, " +
                StepsEntry.COLUMN_STEPS_DESCRIPTION + " TEXT NOT NULL, " +
                StepsEntry.COLUMN_STEPS_VIDEO_URL + " TEXT, " +
                StepsEntry.COLUMN_STEPS_THUMBNAIL_URL + " TEXT)";

        // Create a table to hold the ingredients data
        final String SQL_CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + IngredientsEntry.TABLE_NAME_INGREDIENTS + " (" +
                IngredientsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                IngredientsEntry.COLUMN_INGREDIENTS_PARENT_ID + " INTEGER NOT NULL, " +
                IngredientsEntry.COLUMN_INGREDIENTS_QUANTITY + " TEXT NOT NULL, " +
                IngredientsEntry.COLUMN_INGREDIENTS_MEASURE + " TEXT NOT NULL, " +
                IngredientsEntry.COLUMN_INGREDIENTS_INGREDIENT + " TEXT NOT NULL)";

        try {
            sqLiteDatabase.execSQL(SQL_CREATE_STEPS_TABLE);
            sqLiteDatabase.execSQL(SQL_CREATE_INGREDIENTS_TABLE);
            sqLiteDatabase.execSQL(SQL_CREATE_RECIPES_TABLE);
        } catch (SQLException sqle) {
            Log.e(TAG, "SQL creating tables error" + sqle);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For this project simply drop the table and create a new one.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IngredientsEntry.TABLE_NAME_INGREDIENTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StepsEntry.TABLE_NAME_STEPS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RecipeEntry.TABLE_NAME_RECIPES);

        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For this project simply drop the table and create a new one.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IngredientsEntry.TABLE_NAME_INGREDIENTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StepsEntry.TABLE_NAME_STEPS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RecipeEntry.TABLE_NAME_RECIPES);

        onCreate(sqLiteDatabase);
    }
}