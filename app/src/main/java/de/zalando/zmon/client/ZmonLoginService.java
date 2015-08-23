package de.zalando.zmon.client;

import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface ZmonLoginService {

    @FormUrlEncoded
    @POST("/login")
    Response login(@Field("j_username") String username, @Field("j_password") String password);
}
