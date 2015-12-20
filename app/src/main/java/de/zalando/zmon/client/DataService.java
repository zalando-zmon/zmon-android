package de.zalando.zmon.client;

import java.util.List;

import de.zalando.zmon.client.domain.AlertDetails;
import de.zalando.zmon.client.domain.AlertHeader;
import de.zalando.zmon.client.domain.ZmonStatus;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface DataService {

    @GET("/api/v1/mobile/alert")
    Call<List<AlertHeader>> listAlertHeaders();

    @GET("/api/v1/mobile/alert/{id}")
    Call<AlertDetails> getAlertDetails(@Path("id") String alertId);

    @GET("/api/v1/mobile/active-alerts")
    Call<List<AlertDetails>> getActiveAlerts();

    @GET("/api/v1/mobile/active-alerts")
    Call<List<AlertDetails>> getActiveAlerts(@Query("team") String team);

    @GET("/api/v1/mobile/all-teams")
    Call<List<String>> listTeams();

    @GET("/api/v1/mobile/status")
    Call<ZmonStatus> getStatus();
}
