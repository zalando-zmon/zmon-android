package de.zalando.zmon.client.interceptor;

import android.content.Context;
import android.util.Log;

import de.zalando.zmon.auth.CredentialsStore;
import retrofit.RequestInterceptor;

public class OAuthTokenInterceptor implements RequestInterceptor {

    private final Context context;

    public OAuthTokenInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public void intercept(RequestFacade request) {
        final String authValue = "Bearer " + getAccessToken();
        request.addHeader("Authorization", authValue);

        Log.d("[auth]", "Added OAuth Authorization Header: " + authValue);
    }

    private String getAccessToken() {
        return new CredentialsStore(context).getAccessToken();
    }
}
