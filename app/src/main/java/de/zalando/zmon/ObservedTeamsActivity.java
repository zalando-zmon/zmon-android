package de.zalando.zmon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import de.zalando.zmon.fragment.TeamListFragment;
import de.zalando.zmon.persistence.Team;

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

        /*
         * // Get the SearchView and set the searchable configuration
         * SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
         * SearchView searchView = (SearchView) menu.findItem(R.id.team_search).getActionView();
         *
         * // Assumes current activity is the searchable activity
         * searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
         * searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
         */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {

            case R.id.add_team :
                startActivity(new Intent(this, RemoteTeamListSelectionActivity.class));
                return true;

            default :
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_observed_teams;
    }
}
