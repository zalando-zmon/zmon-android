package de.zalando.zmon.client.interceptor;

import android.content.Context;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import de.zalando.zmon.auth.CredentialsStore;

public class OAuthTokenInterceptor implements Interceptor {

    private final Context context;

    public OAuthTokenInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final String authValue = "Bearer " + getAccessToken();

        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .addHeader("Authorization", authValue)
                .method(request.method(), request.body())
                .build();

        return chain.proceed(newRequest);
    }

    private String getAccessToken() {
        return new CredentialsStore(context).getAccessToken();
    }
}
