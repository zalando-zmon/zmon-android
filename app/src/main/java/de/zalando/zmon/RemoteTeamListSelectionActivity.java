package de.zalando.zmon;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import de.zalando.zmon.fragment.TeamListFragment;
import de.zalando.zmon.persistence.Team;
import de.zalando.zmon.task.GetZmonTeamsTask;

import java.util.ArrayList;
import java.util.List;

public class RemoteTeamListSelectionActivity extends BaseActivity implements TeamListFragment.Callback {

    private TeamListFragment teamListFragment;

    final List<String> teamNameList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remote_teamlist;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teamListFragment = new TeamListFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.teams_fragment, teamListFragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        new GetZmonTeamsTask((ZmonApplication) getApplication(), new GetZmonTeamsTask.Callback() {
                @Override
                public void onSuccess(final List<String> teams) {
                    Log.i("zmon", "Successfully fetched " + teams.size() + " teams");
                    teamNameList.addAll(teams);
                }

                @Override
                public void onError(final Exception e) {
                    Toast.makeText(RemoteTeamListSelectionActivity.this, "Error while fetching teams!",
                        Toast.LENGTH_SHORT).show();
                }
            }).execute();
        transFormTeamListAndSetFragment(teamNameList);
    }

    @Override
    public void onTeamSelected(final Team team) {
        new AlertDialog.Builder(this).setTitle(R.string.remoteteamlist_dialog_really_observe_title)
                                     .setMessage(getString(R.string.remoteteamlist_dialog_really_observe_message,
                                             team.getName()))
                                     .setPositiveButton(R.string.remoteteamlist_dialog_really_observe_positive_button,
                                         new DialogInterface.OnClickListener() {
                                             @Override
                                             public void onClick(final DialogInterface dialogInterface, final int i) {
                                                 team.setObserved(true);
                                                 team.save();

                                                 finish();
                                             }
                                         })
                                     .setNegativeButton(R.string.remoteteamlist_dialog_really_observe_negative_button,
                                         new DialogInterface.OnClickListener() {
                                             @Override
                                             public void onClick(final DialogInterface dialogInterface, final int i) {
                                                 dialogInterface.dismiss();
                                             }
                                         }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_remote_team_list, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_team).getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(final String query) {
                    Log.i("[SEARCH]", "Searching for teams with name: " + query);

                    getAndFilterTeams(query);

                    return true;
                }

                @Override
                public boolean onQueryTextChange(final String newText) {
                    Log.i("[SEARCH]", "Searching for teams with name: " + newText);

                    getAndFilterTeams(newText);
                    return true;
                }
            });

        return true;
    }

    private void getAndFilterTeams(final String query) {

        // Filter all teams
        List<String> filteredTeams = new ArrayList<>(Collections2.filter(teamNameList, new Predicate<String>() {
                        @Override
                        public boolean apply(final String input) {
                            return input.toLowerCase().contains(query.toLowerCase());
                        }
                    }));

        // Map to Team entities
        transFormTeamListAndSetFragment(filteredTeams);
    }

    private void transFormTeamListAndSetFragment(final List<String> teams) {
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
}
