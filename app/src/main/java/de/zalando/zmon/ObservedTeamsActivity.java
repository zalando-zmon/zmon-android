package de.zalando.zmon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import de.zalando.zmon.fragment.TeamListFragment;
import de.zalando.zmon.persistence.Team;

public class ObservedTeamsActivity extends BaseActivity {

    private TeamListFragment teamListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        teamListFragment = new TeamListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, teamListFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        new AsyncTask<Void, Void, List<Team>>() {
            @Override
            protected List<Team> doInBackground(Void... voids) {
                return Team.listAll(Team.class);
            }

            @Override
            protected void onPostExecute(List<Team> teams) {
                teamListFragment.setTeams(teams);
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_team_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_team:
                startActivity(new Intent(this, RemoteTeamListSelectionActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_observed_teams;
    }
}
