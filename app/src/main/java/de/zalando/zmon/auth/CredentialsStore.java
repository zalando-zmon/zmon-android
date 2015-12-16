package de.zalando.zmon.auth;

import android.content.Context;
import android.content.SharedPreferences;

public class CredentialsStore {

    private static final String PREFS_AUTHORIZATION = "prefs.zmon.credentials";
    private static final String PREFS_USERNAME = "prefs.zmon.credentials.username";
    private static final String PREFS_PASSWORD = "prefs.zmon.credentials.password";
    private static final String PREFS_SAVE_CREDENTIALS = "prefs.zmon.credentials.savecredentials";
    private static final String PREFS_ACCESS_TOKEN = "prefs.zmon.credentials.accesstoken";

    private final Context context;

    public CredentialsStore(Context context) {
        this.context = context;
    }

    public void setCredentials(Credentials credentials) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_AUTHORIZATION, Context.MODE_PRIVATE);
        prefs.edit().putString(PREFS_USERNAME, credentials.getUsername()).apply();
        prefs.edit().putString(PREFS_PASSWORD, credentials.getPassword()).apply();
    }

    public Credentials getCredentials() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_AUTHORIZATION, Context.MODE_PRIVATE);
        return new Credentials(prefs.getString(PREFS_USERNAME, null), prefs.getString(PREFS_PASSWORD, null));
    }

    public void setSaveCredentials(boolean saveCredentials) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_AUTHORIZATION, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(PREFS_SAVE_CREDENTIALS, saveCredentials).apply();
    }

    public boolean getSaveCredentials() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_AUTHORIZATION, Context.MODE_PRIVATE);
        return prefs.getBoolean(PREFS_SAVE_CREDENTIALS, false);
    }

    public void setAccessToken(String accessToken) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_ACCESS_TOKEN, Context.MODE_PRIVATE);
        prefs.edit().putString(PREFS_ACCESS_TOKEN, accessToken).apply();
    }

    public String getAccessToken() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_ACCESS_TOKEN, Context.MODE_PRIVATE);
        return prefs.getString(PREFS_ACCESS_TOKEN, null);
    }
}
