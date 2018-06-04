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
package vkurman.bakingapp.IdlingResource;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.util.Log;

/**
 * RecipeCountingIdlingRecource
 * Created by Vassili Kurman on 02/06/2018.
 * Version 1.0
 */
public class RecipeCountingIdlingRecource {

    private static final String RESOURCE = RecipeCountingIdlingRecource.class.getSimpleName();

    private static CountingIdlingResource mCountingIdlingResource =
            new CountingIdlingResource(RESOURCE);

    public static void increment() {
        Log.d(RESOURCE, "Incrementing resource");
        mCountingIdlingResource.increment();
    }

    public static void decrement() {
        Log.d(RESOURCE, "Decrementing resource");
        mCountingIdlingResource.decrement();
    }

    /**
     * Getter for IdlingRecource
     *
     * @return IdlingResource
     */
    public static IdlingResource getIdlingResource() {
        return mCountingIdlingResource;
    }
}