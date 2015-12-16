package de.zalando.zmon.client;

import java.util.List;

import de.zalando.zmon.client.domain.ZmonAlertStatus;
import de.zalando.zmon.client.domain.ZmonStatus;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ZmonService {

    @GET("/api/v1/mobile/alert")
    List<ZmonAlertStatus> listAlerts();

    @GET("/api/v1/mobile/alert")
    List<ZmonAlertStatus> listAlerts(String teams);

    @GET("/api/v1/mobile/alert/{id}")
    List<ZmonAlertStatus> getAlert(@Path("id") long alertId);

    @GET("/api/v1/mobile/all-teams")
    List<String> listTeams();

    @GET("/api/v1/mobile/status")
    ZmonStatus getStatus();
}
