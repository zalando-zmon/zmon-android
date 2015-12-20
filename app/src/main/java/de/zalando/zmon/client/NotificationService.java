package de.zalando.zmon.client;

import java.util.List;

import de.zalando.zmon.client.domain.AlertSubscription;
import de.zalando.zmon.client.domain.DeviceSubscription;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface NotificationService {

    @POST("/api/v1/subscription")
    Call<Void> registerAlert(@Body AlertSubscription subscription);

    @GET("/api/v1/user/subscriptions")
    Call<List<String>> listSubscriptions();

    @DELETE("/api/v1/subscription/{alertId}")
    Call<Void> unregisterAlert(@Path("alertId") String alertId);

    @POST("/api/v1/device")
    Call<Void> registerDevice(@Body DeviceSubscription subscription);
}
