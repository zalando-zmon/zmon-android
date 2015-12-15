package de.zalando.zmon;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;

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
        Collection<String> teamNames = Team.getAllObservedTeamNames();

        new GetZmonAlertsTask((ZmonApplication) getApplication(), new GetZmonAlertsTask.Callback() {
            @Override
            public void onError(Exception e) {
                ZmonDashboardActivity.this.displayError(e);
            }

            @Override
            public void onResult(final List<ZmonAlertStatus> alertStatusList) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertListFragment.setZmonAlertStatus(alertStatusList);
                    }
                });
            }
        }).execute(teamNames.toArray(new String[teamNames.size()]));
    }

    @Override
    public void clickedAlert(ZmonAlertStatus alert) {
        startActivity(new AlertDetailActivity.AlertDetailActivityIntent(
                this,
                alert.getAlertDefinition().getId()));
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
}
