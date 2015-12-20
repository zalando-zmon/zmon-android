package de.zalando.zmon.client.interceptor;

import android.content.Context;
import android.util.Base64;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import de.zalando.zmon.auth.Credentials;
import de.zalando.zmon.auth.CredentialsStore;

public class BasicAuthRequestInterceptor implements Interceptor {

    private final Context context;

    public BasicAuthRequestInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final String header = "Basic " + createAuthorizationHeader();

        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .addHeader("Authorization", header)
                .method(request.method(), request.body())
                .build();

        return chain.proceed(newRequest);
    }

    private String createAuthorizationHeader() {
        Credentials credentials = new CredentialsStore(context).getCredentials();
        String basicAuthValue = credentials.getUsername() + ":" + credentials.getPassword();

        return Base64.encodeToString(basicAuthValue.getBytes(), Base64.NO_WRAP);
    }
}
