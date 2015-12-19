package de.zalando.zmon.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesHelper {

    private static final String ZMON_BASE_URL = "https://zmon-notification-service.stups.zalan.do";
    private static final String HTTPS_TOKEN_AUTH_ZALANDO_COM = "https://token.auth.zalando.com";
    private static final String CONTENT_SETTINGS_SYSTEM_NOTIFICATION_SOUND = "content://settings/system/notification_sound";

    private static final String PREF_OAUTH_TOKEN_SERVICE_URL = "zmon_oauth_token_service_url";
    private static final String PREF_NOTIFICATION_SERVICE_URL = "zmon_notification_service_url";

    private static final String PREF_NOTIFICATION_SHOULD_PLAY_SOUND = "notifications_should_play_sound";
    private static final String PREF_NOTIFICATION_ENABLED = "notifications_new_alert";
    private static final String PREF_NOTIFICATION_RINGTONE = "zmon_notifications_new_alert_ringtone";
    private static final String PREF_NOTIFICATION_SHOULD_VIBRATE = "zmon_notifications_new_alert_vibrate";

    private final SharedPreferences settings;

    public PreferencesHelper(Context context) {
        this.settings = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // General settings
    public String getOauthTokenUrl() {
        return settings.getString(PREF_OAUTH_TOKEN_SERVICE_URL, HTTPS_TOKEN_AUTH_ZALANDO_COM);
    }

    public String getNotificationServiceUrl() {
        return settings.getString(PREF_NOTIFICATION_SERVICE_URL, ZMON_BASE_URL);
    }

    // Notification Settings
    public boolean isAlertNotificationActive() {
        return settings.getBoolean(PREF_NOTIFICATION_ENABLED, true);
    }

    public boolean shouldNotificationPlaySound() {
        return settings.getBoolean(PREF_NOTIFICATION_SHOULD_PLAY_SOUND, true);
    }

    public String getNewAlertRingtone() {
        return settings.getString(PREF_NOTIFICATION_RINGTONE, CONTENT_SETTINGS_SYSTEM_NOTIFICATION_SOUND);
    }

    public boolean shouldNotificationVibrate() {
        return settings.getBoolean(PREF_NOTIFICATION_SHOULD_VIBRATE, true);
    }

}
