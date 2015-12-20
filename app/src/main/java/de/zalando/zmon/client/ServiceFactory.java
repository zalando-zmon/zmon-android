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
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import de.zalando.zmon.client.interceptor.BasicAuthRequestInterceptor;
import de.zalando.zmon.client.interceptor.ErrorHandlerInterceptor;
import de.zalando.zmon.client.interceptor.OAuthTokenInterceptor;
import de.zalando.zmon.util.PreferencesHelper;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class ServiceFactory {

    public static OAuthAccessTokenService createOAuthService(Context context) {
        PreferencesHelper preferencesHelper = new PreferencesHelper(context);

        OkHttpClient httpClient = createClient();
        httpClient.interceptors().add(new BasicAuthRequestInterceptor(context));

        return new Retrofit.Builder()
                .baseUrl(preferencesHelper.getOauthTokenUrl())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .client(httpClient)
                .build()
                .create(OAuthAccessTokenService.class);
    }

    public static DataService createDataService(Context context) {
        PreferencesHelper preferencesHelper = new PreferencesHelper(context);

        OkHttpClient httpClient = createClient();
        httpClient.interceptors().add(new OAuthTokenInterceptor(context));

        return new Retrofit.Builder()
                .baseUrl(preferencesHelper.getDataServiceUrl())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .client(httpClient)
                .build()
                .create(DataService.class);
    }

    public static NotificationService createNotificationService(Context context) {
        PreferencesHelper preferencesHelper = new PreferencesHelper(context);

        OkHttpClient httpClient = createClient();
        httpClient.interceptors().add(new OAuthTokenInterceptor(context));

        return new Retrofit.Builder()
                .baseUrl(preferencesHelper.getNotificationServiceUrl())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .client(httpClient)
                .build()
                .create(NotificationService.class);
    }

    private static OkHttpClient createClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(5000, TimeUnit.MILLISECONDS);
        client.setReadTimeout(15000, TimeUnit.MILLISECONDS);
        client.interceptors().add(createLoggingInterceptor());
        client.networkInterceptors().add(new ErrorHandlerInterceptor());

        return client;
    }

    private static HttpLoggingInterceptor createLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
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
