package de.zalando.zmon.client;

import java.util.List;

import de.zalando.zmon.client.domain.AlertDetails;
import de.zalando.zmon.client.domain.AlertHeader;
import de.zalando.zmon.client.domain.ZmonAlertStatus;
import de.zalando.zmon.client.domain.ZmonStatus;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ZmonService {

    @GET("/api/v1/mobile/alert")
    List<AlertHeader> listAlertHeaders();

    @GET("/api/v1/mobile/alert/{id}")
    AlertDetails getAlertDetails(@Path("id") String alertId);

    @Deprecated
    @GET("/api/v1/mobile/alert")
    List<ZmonAlertStatus> listAlerts();

    @Deprecated
    @GET("/api/v1/mobile/alert")
    List<ZmonAlertStatus> listAlerts(@Query("teams") String teams);

    @Deprecated
    @GET("/api/v1/mobile/alert/{id}")
    List<ZmonAlertStatus> getAlert(@Path("id") long alertId);

    @GET("/api/v1/mobile/all-teams")
    List<String> listTeams();

    @GET("/api/v1/mobile/status")
    ZmonStatus getStatus();
}
