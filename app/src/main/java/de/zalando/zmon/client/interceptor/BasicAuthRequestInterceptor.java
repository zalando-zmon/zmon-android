package de.zalando.zmon.client.interceptor;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import de.zalando.zmon.auth.Credentials;
import de.zalando.zmon.auth.CredentialsStore;
import retrofit.RequestInterceptor;

public class BasicAuthRequestInterceptor implements RequestInterceptor {

    private final Context context;

    public BasicAuthRequestInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public void intercept(RequestFacade request) {
        String header = "Basic " + createAuthorizationHeader();
        request.addHeader("Authorization", header);

        Log.d("[login]", "Added Basic Authorization Http Header: " + header);
    }

    private String createAuthorizationHeader() {
        Credentials credentials = new CredentialsStore(context).getCredentials();
        String basicAuthValue = credentials.getUsername() + ":" + credentials.getPassword();

        return Base64.encodeToString(basicAuthValue.getBytes(), Base64.NO_WRAP);
    }
}
