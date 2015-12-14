package de.zalando.zmon.client;

import java.util.List;

import de.zalando.zmon.client.domain.ZmonAlertStatus;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ZmonAlertsService {

    @GET("/rest/allAlerts")
    List<ZmonAlertStatus> list();

    @GET("/rest/allAlerts")
    List<ZmonAlertStatus> listByTeam(@Query("team") String team);
}
