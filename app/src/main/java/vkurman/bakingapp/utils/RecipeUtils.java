package vkurman.bakingapp.utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * RecipeUtils
 * Created by Vassili Kurman on 07/04/2018.
 * Version 1.0
 */

public class RecipeUtils {

    private static final String TAG = "RecipeUtils";

    private static final String API_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    /**
     * Build and returns URL for recipes.
     *
     * @return URL
     */
    public static final URL createRecipesUrl() {
        try {
            return new URL(API_BASE_URL);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error building URL!");
        }

        return null;
    }

    /**
     * Returns response from specified URL in json format.
     *
     * @param url - url to specific query
     * @return String - response from server in json format
     * @throws IOException - throws when can't open url connection
     */
    public static final String getJsonResponseFromWeb(URL url) throws IOException {
        if(url != null) {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                if(scanner.hasNext()) {
                    return scanner.next();
                }
            } finally {
                urlConnection.disconnect();
            }
        }
        return null;
    }

}