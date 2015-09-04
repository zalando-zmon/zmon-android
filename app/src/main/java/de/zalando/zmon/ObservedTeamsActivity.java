package de.zalando.zmon;

import android.os.Bundle;

import com.google.common.collect.Lists;

import de.zalando.zmon.client.domain.ZmonTeam;
import de.zalando.zmon.fragment.TeamListFragment;

public class ObservedTeamsActivity extends BaseActivity {

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

        // TODO search teams from persistence layer
        teamListFragment.setTeams(Lists.newArrayList(ZmonTeam.of("Team A"), ZmonTeam.of("Team B")));
    }
}
