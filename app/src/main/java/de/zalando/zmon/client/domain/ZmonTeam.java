package de.zalando.zmon.client.domain;

import com.google.common.base.MoreObjects;

public class ZmonTeam {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", name).toString();
    }

    public static ZmonTeam of(String name) {
        ZmonTeam team = new ZmonTeam();
        team.setName(name);

        return team;
    }
}
