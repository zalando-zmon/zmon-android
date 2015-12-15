package de.zalando.zmon;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import de.zalando.zmon.fragment.TeamListFragment;
import de.zalando.zmon.persistence.Team;
import de.zalando.zmon.task.GetZmonTeamsTask;

import android.os.Bundle;

import android.util.Log;

import android.widget.SearchView;
import android.widget.Toast;

public class ObservedTeamsActivity extends BaseActivity implements TeamListFragment.Callback {

    private TeamListFragment teamListFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_observed_teams;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        teamListFragment = new TeamListFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.teams_fragment, teamListFragment).commit();

        teamListFragment.getTeamSearch().setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(final String queryString) {
                    final Predicate<String> teamNameFilter = new Predicate<String>() {
                        @Override
                        public boolean apply(final String input) {
                            return input.contains(queryString);
                        }
                    };

                    new GetZmonTeamsTask((ZmonApplication) getApplication(), new GetZmonTeamsTask.Callback() {
                            @Override
                            public void onSuccess(final List<String> teams) {
                                Log.i("zmon", "Successfully fetched " + teams.size() + " teams");

                                List<Team> teamList = Lists.transform(teams, new Function<String, Team>() {
                                            @Override
                                            public Team apply(final String name) {
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
                            public void onError(final Exception e) {
                                Toast.makeText(ObservedTeamsActivity.this, "Error while fetching teams!",
                                    Toast.LENGTH_SHORT).show();
                            }
                        }).execute();

                    return false;
                }

                @Override
                public boolean onQueryTextChange(final String s) {
                    return false;
                }
            });

    }

    @Override
    protected void onStart() {
        super.onStart();

        new GetZmonTeamsTask((ZmonApplication) getApplication(), new GetZmonTeamsTask.Callback() {
                @Override
                public void onSuccess(final List<String> teams) {
                    Log.i("zmon", "Successfully fetched " + teams.size() + " teams");

                    List<Team> teamList = Lists.transform(teams, new Function<String, Team>() {
                                @Override
                                public Team apply(final String name) {
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
                public void onError(final Exception e) {
                    Toast.makeText(ObservedTeamsActivity.this, "Error while fetching teams!", Toast.LENGTH_SHORT)
                         .show();
                }
            }).execute();
    }

    @Override
    public void onObserveTeam(final Team team) {
        team.setObserved(true);
        team.save();
    }

    @Override
    public void onUnobserveTeam(final Team team) {
        team.setObserved(false);
        team.save();
    }
}
