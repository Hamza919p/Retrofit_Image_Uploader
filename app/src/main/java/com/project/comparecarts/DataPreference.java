package com.project.comparecarts;

import android.content.Context;
import android.content.SharedPreferences;

public class DataPreference {
    private static SharedPreferences prefs;

    Context context;
    private String userId = "userId";

    public DataPreference(Context context) {
        this.context = context;
    }

    private static DataPreference instance = null;

    public static DataPreference getInstance(Context context) {
        if (instance == null) {
            instance = new DataPreference(context);
            prefs = context.getSharedPreferences(
                    context.getString(R.string.app_name),
                    Context.MODE_PRIVATE
            );
        }
        return instance;
    }

    public void setUserId(String userId) {
        prefs.edit().putString(this.userId, userId).apply();
    }

    public String getUserId() {
        return prefs.getString(this.userId, "");
    }

}
