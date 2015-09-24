package de.zalando.zmon;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

import de.zalando.zmon.fragment.TeamListFragment;
import de.zalando.zmon.persistence.Team;
import de.zalando.zmon.task.GetZmonTeamsTask;

public class ObservedTeamsActivity extends BaseActivity implements TeamListFragment.Callback {

    private TeamListFragment teamListFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_observed_teams;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        teamListFragment = new TeamListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.teams_fragment, teamListFragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        new GetZmonTeamsTask((ZmonApplication) getApplication(), new GetZmonTeamsTask.Callback() {
            @Override
            public void onSuccess(List<String> teams) {
                Log.i("zmon", "Successfully fetched " + teams.size() + " teams");

                List<Team> teamList = Lists.transform(teams, new Function<String, Team>() {
                    @Override
                    public Team apply(String name) {
                        List<Team> teams = Team.find(Team.class, "name = ?", name);
                        if (teams == null || teams.isEmpty()) {
                            return Team.of(name);
                        } else {
                            return teams.get(0);
                        }
                    }
                });
                teamListFragment.setTeams(teamList);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(ObservedTeamsActivity.this, "Error while fetching teams!", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    @Override
    public void onObserveTeam(Team team) {
        team.setObserved(true);
        team.save();
    }

    @Override
    public void onUnobserveTeam(Team team) {
        team.setObserved(false);
        team.save();
    }
}
