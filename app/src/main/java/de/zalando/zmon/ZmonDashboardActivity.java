package de.zalando.zmon;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;

import java.util.Collection;
import java.util.List;

import de.zalando.zmon.client.domain.ZmonAlertStatus;
import de.zalando.zmon.fragment.ZmonAlertListFragment;
import de.zalando.zmon.persistence.Team;

public class ZmonDashboardActivity extends SelfUpdatableActivity {

    private ZmonAlertListFragment alertListFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alertListFragment = new ZmonAlertListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.alert_list_fragment, alertListFragment)
                .commit();
    }

    @Override
    protected void runJob() {
        List<Team> teams = Team.listAll(Team.class);
        Collection<String> teamNames = Collections2.transform(teams, new Function<Team, String>() {
            @Override
            public String apply(Team team) {
                return team.getName();
            }
        });

        new GetZmonAlertsTask((ZmonApplication) getApplication()) {
            @Override
            protected void onPostExecute(final List<ZmonAlertStatus> zmonAlertStatuses) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertListFragment.setZmonAlertStatus(zmonAlertStatuses);
                    }
                });
            }
        }.execute(teamNames.toArray(new String[teamNames.size()]));
    }

    private void displayError(final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getParent())
                        .setTitle(R.string.error_network)
                        .setMessage(e.getMessage())
                        .show();
            }
        });
    }

    private class GetZmonAlertsTask extends AsyncTask<String, Void, List<ZmonAlertStatus>> {

        private final ZmonApplication zmonApplication;

        private GetZmonAlertsTask(ZmonApplication zmonApplication) {
            this.zmonApplication = zmonApplication;
        }

        @Override
        protected List<ZmonAlertStatus> doInBackground(String... teams) {
            try {
                String teamQueryString = makeTeamString(teams);
                Log.d("zmon", "Query alerts for teams: " + teamQueryString);

                return zmonApplication.getZmonAlertsService().listByTeam(teamQueryString);
            } catch (Exception e) {
                Log.e("[zmon]", "Error while fetching alerts", e);
                displayError(e);
            }

            return null;
        }

        private String makeTeamString(String... teams) {
            return Joiner.on(",").join(teams);
        }
    }
}
