package de.zalando.zmon.util;

import android.content.Context;
import android.content.SharedPreferences;

public class InstanceIdTokenStore {

    private final String SHARED_PREFS_INSTANCEID = "prefs.instanceid";
    private final String PREF_INSTANCEID_TOKEN = "pref.instanceid.token";

    private final Context context;

    public InstanceIdTokenStore(Context context) {
        this.context = context;
    }

    public String getToken() {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS_INSTANCEID, Context.MODE_PRIVATE);
        return prefs.getString(PREF_INSTANCEID_TOKEN, null);
    }

    public boolean setToken(String token) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS_INSTANCEID, Context.MODE_PRIVATE);
        return prefs.edit().putString(PREF_INSTANCEID_TOKEN, token).commit();
    }
}
