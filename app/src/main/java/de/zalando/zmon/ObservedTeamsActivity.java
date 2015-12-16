package de.zalando.zmon;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class ObservedTeamsActivity extends BaseActivity {

    private TeamListFragment teamListFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        teamListFragment = new TeamListFragment();

        Toolbar topToolbar = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(topToolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.content, teamListFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        new AsyncTask<Void, Void, List<Team>>() {
            @Override
            protected List<Team> doInBackground(final Void... voids) {
                return Team.listAll(Team.class);
            }

            @Override
            protected void onPostExecute(final List<Team> teams) {
                teamListFragment.setTeams(teams);
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_team_list, menu);

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
                    return false;
                }
            });

        return true;
    }

    private void getAndFilterTeams(final String query) {

        new GetZmonTeamsTask((ZmonApplication) getApplication(), new GetZmonTeamsTask.Callback() {
                @Override
                public void onSuccess(final List<String> teams) {
                    Log.i("zmon", "Successfully fetched " + teams.size() + " teams");

                    Predicate<String> teamFilter = new Predicate<String>() {
                        @Override
                        public boolean apply(final String input) {
                            return input.toLowerCase().contains(query.toLowerCase());
                        }
                    };

                    List<String> filteredTeams = new ArrayList<String>(Collections2.filter(teams, teamFilter));

                    List<Team> teamList = Lists.transform(filteredTeams, new Function<String, Team>() {
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
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {

            case R.id.add_team :
                startActivity(new Intent(this, RemoteTeamListSelectionActivity.class));
                return true;

            case R.id.search_team :

                return true;

            default :
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(final Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.i("[Search]", "Received Query" + query);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_observed_teams;
    }
}
