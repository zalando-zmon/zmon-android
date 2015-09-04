package de.zalando.zmon.persistence;

import com.google.common.base.MoreObjects;
import com.orm.SugarRecord;

public class Team extends SugarRecord<Team> {

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

    public static Team of(String name) {
        Team team = new Team();
        team.setName(name);

        return team;
    }
}
