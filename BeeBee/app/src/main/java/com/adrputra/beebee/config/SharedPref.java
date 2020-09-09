package com.adrputra.beebee.config;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private Context context;
    private SharedPreferences sharedPreferences;

    private static final String ACCESS_TOKEN = "_.ACCESS_TOKEN";
    private static final String JWT_TOKEN    = "_.JWT_TOKEN";
    private static final String USER_ID      = "_.USER_ID";

    public SharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("MAIN_PREF", Context.MODE_PRIVATE);
    }

    public void setAccessToken(String accessToken) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, accessToken).apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN, "");
    }

    public void setJwt(String jwt) {
        sharedPreferences.edit().putString(JWT_TOKEN, jwt).apply();
    }

    public String getJwt() {
        return sharedPreferences.getString(JWT_TOKEN, "");
    }

    public void setUserId(Integer userId) {
        sharedPreferences.edit().putInt(USER_ID, userId).apply();
    }

    public Integer getUserId() {
        return sharedPreferences.getInt(USER_ID, 0);
    }
}
