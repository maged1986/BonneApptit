package com.mego.bonneapptit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
@Inject
    public SessionManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences("SessionManager", Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }
    // session manager keys
    private static final String IS_LOGGED_IN = "logged_in";

    public void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn).apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }
}
