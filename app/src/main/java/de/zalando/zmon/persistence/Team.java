package de.zalando.zmon.persistence;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Collections2;
import com.orm.SugarRecord;

import java.util.Collection;
import java.util.List;

public class Team extends SugarRecord<Team> {

    private String name;

    private boolean observed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isObserved() {
        return observed;
    }

    public void setObserved(boolean observed) {
        this.observed = observed;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", name).add("observed", observed).toString();
    }

    public static Team of(String name) {
        Team team = new Team();
        team.setName(name);

        return team;
    }

    public static Collection<String> getAllTeamNames() {
        List<Team> teams = Team.listAll(Team.class);
        return Collections2.transform(teams, new Function<Team, String>() {
            @Override
            public String apply(Team team) {
                return team.getName();
            }
        });
    }
}
