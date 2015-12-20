package de.zalando.zmon.client;

import java.util.List;

import de.zalando.zmon.client.domain.AlertDetails;
import de.zalando.zmon.client.domain.AlertHeader;
import de.zalando.zmon.client.domain.ZmonStatus;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface DataService {

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
}
