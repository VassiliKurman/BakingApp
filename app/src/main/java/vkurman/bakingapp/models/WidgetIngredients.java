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
package vkurman.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * WidgetIngredients
 * Created by Vassili Kurman on 18/05/2018.
 * Version 1.0
 */
public class WidgetIngredients implements Parcelable {

    private int recipeId;
    private String recipeName;
    private int servings;
    private Ingredient ingredients;

    public static final Parcelable.Creator<WidgetIngredients> CREATOR
            = new Parcelable.Creator<WidgetIngredients>() {
        public WidgetIngredients createFromParcel(Parcel in) {
            return new WidgetIngredients(in);
        }

        public WidgetIngredients[] newArray(int size) {
            return new WidgetIngredients[size];
        }
    };

    private WidgetIngredients(Parcel in) {
        recipeId = in.readInt();
        recipeName = in.readString();
        servings = in.readInt();
        ingredients = in.readTypedObject(Ingredient.CREATOR);
    }

    public WidgetIngredients(int recipeId, String recipeName, int servings, Ingredient ingredients) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.servings = servings;
        this.ingredients = ingredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipeId);
        dest.writeString(recipeName);
        dest.writeInt(servings);
        dest.writeTypedObject(ingredients, 0);
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public Ingredient getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient ingredients) {
        this.ingredients = ingredients;
    }
}