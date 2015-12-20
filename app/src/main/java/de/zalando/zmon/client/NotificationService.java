package de.zalando.zmon.client;

import java.util.List;

import de.zalando.zmon.client.domain.AlertSubscription;
import de.zalando.zmon.client.domain.DeviceSubscription;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface NotificationService {

    @POST("/api/v1/subscription")
    Response registerAlert(@Body AlertSubscription subscription);

    @GET("/api/v1/user/subscriptions")
    List<String> listSubscriptions();

    @DELETE("/api/v1/subscription/{alertId}")
    Response unregisterAlert(@Path("alertId") String alertId);

    @POST("/api/v1/device")
    Response registerDevice(@Body DeviceSubscription subscription);
}
