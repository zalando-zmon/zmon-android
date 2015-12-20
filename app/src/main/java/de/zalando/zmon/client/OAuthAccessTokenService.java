package de.zalando.zmon.client;

import retrofit.Call;
import retrofit.http.GET;

public interface OAuthAccessTokenService {

    @GET("/access_token")
    Call<String> login();
}
