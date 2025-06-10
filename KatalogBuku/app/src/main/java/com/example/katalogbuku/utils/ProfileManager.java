package com.example.katalogbuku.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ProfileManager {

    private static final String PREFS_NAME = "profile_prefs";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_AVATAR_URI = "avatar_uri";
    private final SharedPreferences sharedPreferences;

    public ProfileManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_NAME, name);
        editor.apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, "Pembaca Setia");
    }

    public void saveAvatarUri(String uriString) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_AVATAR_URI, uriString);
        editor.apply();
    }

    public String getAvatarUri() {
        return sharedPreferences.getString(KEY_AVATAR_URI, null);
    }
}
