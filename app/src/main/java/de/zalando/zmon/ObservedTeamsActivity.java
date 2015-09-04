package de.zalando.zmon;

import android.os.Bundle;

import java.util.List;

import de.zalando.zmon.fragment.TeamListFragment;
import de.zalando.zmon.persistence.Team;

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

        teamListFragment.setTeams(Team.listAll(Team.class));
    }

    @Override
    public void onCreateNewTeam(Team team) {
        team.save();
        List<Team> teams = Team.listAll(Team.class);
        teamListFragment.setTeams(teams);
    }
}
