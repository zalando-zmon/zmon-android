package de.zalando.zmon.client;

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
import de.zalando.zmon.client.profiler.LoggingProfiler;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class ServiceFactory {

    public static ZmonLoginService createZmonLoginService() {
        return new RestAdapter.Builder()
                .setEndpoint("https://zmon2.zalando.net")
                .setErrorHandler(new ZmonErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setProfiler(new LoggingProfiler())
                .build()
                .create(ZmonLoginService.class);
    }

    public static ZmonStatusService createZmonStatusService() {
        return new RestAdapter.Builder()
                .setEndpoint("https://zmon2.zalando.net")
                .setErrorHandler(new ZmonErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setProfiler(new LoggingProfiler())
                .setConverter(new GsonConverter(createGson()))
                .build()
                .create(ZmonStatusService.class);
    }

    public static ZmonAlertsService createZmonAlertService() {
        return new RestAdapter.Builder()
                .setEndpoint("https://zmon2.zalando.net")
                .setErrorHandler(new ZmonErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setProfiler(new LoggingProfiler())
                .setConverter(new GsonConverter(createGson()))
                .build()
                .create(ZmonAlertsService.class);
    }

    public static ZmonTeamService createZmonTeamService() {
        return new RestAdapter.Builder()
                .setEndpoint("https://zmon2.zalando.net")
                .setErrorHandler(new ZmonErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setProfiler(new LoggingProfiler())
                .setConverter(new GsonConverter(createGson()))
                .build()
                .create(ZmonTeamService.class);
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
