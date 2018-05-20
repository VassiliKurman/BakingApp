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
package vkurman.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import vkurman.bakingapp.R;
import vkurman.bakingapp.RecipesActivity;
import vkurman.bakingapp.ui.RecipeDetailsActivity;
import vkurman.bakingapp.utils.BakingAppConstants;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int recipeId, String recipeName) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        // Create an Intent to launch RecipeDetailsActivity
//        Intent clickIntent = new Intent(context, RecipeDetailsActivity.class);
//        clickIntent.putExtra(BakingAppConstants.INTENT_NAME_FOR_RECIPE_ID, recipeId);
//        clickIntent.putExtra(BakingAppConstants.INTENT_NAME_FOR_RECIPE_NAME, recipeName);
        Intent clickIntent = new Intent(context, RecipesActivity.class);
        PendingIntent pendingClickIntent = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_root, pendingClickIntent);
        views.setTextViewText(R.id.tv_recipe_name, recipeId + " - " + recipeName);

        // Set the list of ingredients for the specified recipe id using RemoteViewsService
        Intent remoteAdapterIntent = new Intent(context, RecipeWidgetService.class);
        remoteAdapterIntent.setData(Uri.fromParts("content", String.valueOf(recipeId), null));
        views.setRemoteAdapter(R.id.lv_widget_ingredients, remoteAdapterIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Do nothing in this implementation
        // TODO use sharedPreferrences to get selected recipe in acivity
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
