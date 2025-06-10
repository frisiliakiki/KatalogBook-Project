package com.example.katalogbuku.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

public class Theme {

    private static final String PREFS_NAME = "theme_prefs";
    private static final String KEY_THEME = "prefs_theme";
    private static final int LIGHT_MODE = 0;
    private static final int DARK_MODE = 1;

    private final SharedPreferences sharedPreferences;

    public Theme (Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Menyimpan pilihan tema
    public void setTheme(boolean isDarkMode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_THEME, isDarkMode ? DARK_MODE : LIGHT_MODE);
        editor.apply();
        applyTheme(); // Langsung terapkan tema
    }

    // Mengecek apakah mode gelap sedang aktif
    public boolean isDarkMode() {
        return sharedPreferences.getInt(KEY_THEME, LIGHT_MODE) == DARK_MODE;
    }

    // Menerapkan tema ke seluruh aplikasi
    public void applyTheme() {
        if (isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}

