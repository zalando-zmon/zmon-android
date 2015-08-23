package de.zalando.zmon.client;

import de.zalando.zmon.client.domain.ZmonStatus;
import retrofit.http.GET;

public interface ZmonStatusService {

    @GET("/rest/status")
    ZmonStatus getStatus();
}
