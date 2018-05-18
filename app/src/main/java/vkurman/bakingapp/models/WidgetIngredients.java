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
    private Ingredient[] ingredients;

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
        ingredients = in.createTypedArray(Ingredient.CREATOR);
    }

    public WidgetIngredients(int recipeId, String recipeName, int servings, Ingredient[] ingredients) {
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
        dest.writeTypedArray(ingredients, 0);
    }
}