package de.zalando.zmon;

import android.os.Bundle;

import java.util.Collection;
import java.util.List;

import de.zalando.zmon.client.domain.ZmonAlertStatus;
import de.zalando.zmon.fragment.ZmonDetailedAlertListFragment;
import de.zalando.zmon.persistence.Team;
import de.zalando.zmon.task.GetZmonAlertsTask;

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

        new GetZmonAlertsTask(this) {
            @Override
            public void onPostExecute(final List<ZmonAlertStatus> alertStatusList) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertListFragment.setZmonAlertStatus(alertStatusList);
                    }
                });
            }
        }.execute(teamNames.toArray(new String[teamNames.size()]));
    }

    @Override
    public void clickedAlert(ZmonAlertStatus alert) {
        startActivity(new AlertDetailActivity.AlertDetailActivityIntent(
                this,
                // TODO change types here
                String.valueOf(alert.getAlertDefinition().getId())));
    }
}
