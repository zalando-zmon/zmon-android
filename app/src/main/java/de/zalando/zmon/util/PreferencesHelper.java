package de.zalando.zmon.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Jochen Buchholz on 18.12.15.
 */
public class PreferencesHelper {
    private static final String ZMON_BASE_URL = "https://zmon-notification-service.stups.zalan.do";
    private static final String HTTPS_TOKEN_AUTH_ZALANDO_COM = "https://token.auth.zalando.com";
    private static final String CONTENT_SETTINGS_SYSTEM_NOTIFICATION_SOUND = "content://settings/system/notification_sound";

    private final SharedPreferences settings;
    public PreferencesHelper(Context context) {
        this.settings = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // General settings
    public String getOauthTokenUrl() {
        return settings.getString("zmon_oauth_token_service_url", HTTPS_TOKEN_AUTH_ZALANDO_COM);
    }

    public String getNotificationServiceUrl() {
        return  settings.getString("zmon_notification_service_url",ZMON_BASE_URL);
    }

    // Notification Settings
    public boolean isAlertNotificationActive(){
        return settings.getBoolean("notifications_new_alert", true);
    }

    public String getNewAlertRingtone() {
        return settings.getString("zmon_notifications_new_alert_ringtone", CONTENT_SETTINGS_SYSTEM_NOTIFICATION_SOUND);
    }

    public boolean isVibrateOn() {
        return settings.getBoolean("zmon_notifications_new_alert_vibrate", true);
    }

}
