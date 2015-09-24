package de.zalando.zmon.client;

import java.util.List;

import retrofit.http.GET;

public interface ZmonTeamService {

    @GET("/rest/allTeams")
    List<String> listTeams();
}
