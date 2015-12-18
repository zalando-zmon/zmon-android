package de.zalando.zmon.client;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.lang.reflect.Type;
import java.util.Date;

import de.zalando.zmon.client.exception.ZmonErrorHandler;
import de.zalando.zmon.client.interceptor.BasicAuthRequestInterceptor;
import de.zalando.zmon.client.interceptor.OAuthTokenInterceptor;
import de.zalando.zmon.client.profiler.LoggingProfiler;
import de.zalando.zmon.util.PreferencesHelper;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class ServiceFactory {

    private static final String ZMON_BASE_URL = "https://zmon-notification-service.stups.zalan.do";

    public static OAuthAccessTokenService createOAuthService(Context context) {
        PreferencesHelper preferencesHelper = new PreferencesHelper(context);
        return new RestAdapter.Builder()
                .setEndpoint(preferencesHelper.getOauthTokenUrl() )
                .setRequestInterceptor(new BasicAuthRequestInterceptor(context))
                .setErrorHandler(new ZmonErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setProfiler(new LoggingProfiler())
                .build()
                .create(OAuthAccessTokenService.class);
    }

    public static ZmonService createZmonService(Context context) {
        PreferencesHelper preferencesHelper = new PreferencesHelper(context);
        return new RestAdapter.Builder()
                .setEndpoint(preferencesHelper.getNotificationServiceUrl())
                .setRequestInterceptor(new OAuthTokenInterceptor(context))
                .setErrorHandler(new ZmonErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setProfiler(new LoggingProfiler())
                .setConverter(new GsonConverter(createGson()))
                .build()
                .create(ZmonService.class);
    }

    private static Gson createGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    }
                })
                .create();
    }
}
