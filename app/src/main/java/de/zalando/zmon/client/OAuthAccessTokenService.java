package de.zalando.zmon.client;

import retrofit.client.Response;
import retrofit.http.GET;

public interface OAuthAccessTokenService {

    @GET("/access_token")
    Response login();
}
