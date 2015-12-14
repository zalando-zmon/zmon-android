package de.zalando.zmon;

import android.content.Intent;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.orm.SugarApp;

import java.lang.reflect.Type;
import java.util.Date;

import de.zalando.zmon.auth.Authorization;
import de.zalando.zmon.client.ZmonAlertsService;
import de.zalando.zmon.client.ZmonLoginService;
import de.zalando.zmon.client.ZmonStatusService;
import de.zalando.zmon.client.ZmonTeamService;
import de.zalando.zmon.client.exception.ZmonErrorHandler;
import de.zalando.zmon.client.profiler.LoggingProfiler;
import de.zalando.zmon.service.AlertPullService;
import de.zalando.zmon.service.SetupInstanceIDService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class ZmonApplication extends SugarApp {

    private Gson gson;

    private ZmonLoginService zmonLoginService;
    private ZmonStatusService zmonStatusService;
    private ZmonAlertsService zmonAlertsService;
    private ZmonTeamService zmonTeamService;

    private Authorization authorization;

    @Override
    public void onCreate() {
        super.onCreate();
        AlertPullService.setup(this);

        startService(new Intent(this, SetupInstanceIDService.class));
    }

    private Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .registerTypeAdapter(Date.class, new DateTypeAdapter())
                    .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            return new Date(json.getAsJsonPrimitive().getAsLong());
                        }
                    })
                    .create();
        }

        return gson;
    }

    public ZmonLoginService getZmonLoginService() {
        if (zmonLoginService == null) {
            zmonLoginService = new RestAdapter.Builder()
                    .setEndpoint("https://zmon2.zalando.net")
                    .setErrorHandler(new ZmonErrorHandler())
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setProfiler(new LoggingProfiler())
                    .build()
                    .create(ZmonLoginService.class);
        }

        return zmonLoginService;
    }

    public ZmonStatusService getZmonStatusService() {
        if (zmonStatusService == null) {
            zmonStatusService = new RestAdapter.Builder()
                    .setEndpoint("https://zmon2.zalando.net")
                    .setErrorHandler(new ZmonErrorHandler())
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setProfiler(new LoggingProfiler())
                    .setConverter(new GsonConverter(getGson()))
                    .build()
                    .create(ZmonStatusService.class);
        }

        return zmonStatusService;
    }

    public ZmonAlertsService getZmonAlertsService() {
        if (zmonAlertsService == null) {
            zmonAlertsService = new RestAdapter.Builder()
                    .setEndpoint("https://zmon2.zalando.net")
                    .setErrorHandler(new ZmonErrorHandler())
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setProfiler(new LoggingProfiler())
                    .setConverter(new GsonConverter(getGson()))
                    .build()
                    .create(ZmonAlertsService.class);
        }

        return zmonAlertsService;
    }

    public ZmonTeamService getZmonTeamService() {
        if (zmonTeamService == null) {
            zmonTeamService = new RestAdapter.Builder()
                    .setEndpoint("https://zmon2.zalando.net")
                    .setErrorHandler(new ZmonErrorHandler())
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setProfiler(new LoggingProfiler())
                    .setConverter(new GsonConverter(getGson()))
                    .build()
                    .create(ZmonTeamService.class);
        }

        return zmonTeamService;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }
}
