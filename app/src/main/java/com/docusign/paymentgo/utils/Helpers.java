package com.docusign.paymentgo.utils;

import android.content.Context;
import android.util.Log;

import com.docusign.paymentgo.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by krshah on 9/25/16.
 */

public class Helpers {

    private static final String TAG = Helpers.class.getCanonicalName();

    public static List<User> getSeedUserData(Context ctx) {
        try {
            InputStream is = ctx.getAssets().open("initialSeedData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonAsString = new String(buffer, "UTF-8");
            Gson gson = new Gson();
            return gson.fromJson(jsonAsString, new TypeToken<List<User>>(){}.getType());
        } catch (IOException ex) {
            Log.e(TAG, "getSeedUserData: ", ex);
            return null;
        }
    }
}
