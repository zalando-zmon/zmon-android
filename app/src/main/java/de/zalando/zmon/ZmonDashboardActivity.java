package de.zalando.zmon;

import android.os.Bundle;

import java.util.Collection;
import java.util.List;

import de.zalando.zmon.fragment.ZmonDetailedAlertListFragment;
import de.zalando.zmon.persistence.AlertDetails;
import de.zalando.zmon.persistence.Team;
import de.zalando.zmon.task.GetActiveAlertsTask;

public class ZmonDashboardActivity extends SelfUpdatableActivity implements ZmonDetailedAlertListFragment.Callback {

    private ZmonDetailedAlertListFragment alertListFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alertListFragment = new ZmonDetailedAlertListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.alert_list_fragment, alertListFragment)
                .commit();
    }

    @Override
    protected void runJob() {
        final Collection<String> teamNames = Team.getAllObservedTeamNames();

        new GetActiveAlertsTask(this) {
            @Override
            protected void onPostExecute(final List<AlertDetails> activeAlerts) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertListFragment.setAlertDetails(activeAlerts);
                    }
                });
            }
        }.execute(teamNames.toArray(new String[teamNames.size()]));
    }

    @Override
    public void clickedAlert(AlertDetails alert) {
        startActivity(new AlertDetailActivity.AlertDetailActivityIntent(
                this,
                alert.getAlertDefinition().getAlertId()));
    }
}
