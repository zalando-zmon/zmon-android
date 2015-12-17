package de.zalando.zmon.client;

import java.util.List;

import de.zalando.zmon.client.domain.AlertDetails;
import de.zalando.zmon.client.domain.AlertHeader;
import de.zalando.zmon.client.domain.AlertSubscription;
import de.zalando.zmon.client.domain.DeviceSubscription;
import de.zalando.zmon.client.domain.ZmonStatus;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ZmonService {

    @GET("/api/v1/mobile/alert")
    List<AlertHeader> listAlertHeaders();

    @GET("/api/v1/mobile/alert/{id}")
    AlertDetails getAlertDetails(@Path("id") String alertId);

    @GET("/api/v1/mobile/active-alerts")
    List<AlertDetails> getActiveAlerts();

    @GET("/api/v1/mobile/active-alerts")
    List<AlertDetails> getActiveAlerts(@Query("team") String team);

    @GET("/api/v1/mobile/all-teams")
    List<String> listTeams();

    @GET("/api/v1/mobile/status")
    ZmonStatus getStatus();

    @POST("/api/v1/subscription")
    Response registerAlert(@Body AlertSubscription subscription);

    @POST("/api/v1/device")
    Response registerDevice(@Body DeviceSubscription subscription);
}
