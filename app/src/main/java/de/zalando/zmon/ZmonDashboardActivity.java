package de.zalando.zmon;

import android.os.Bundle;
import android.util.Log;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import java.util.Collection;
import java.util.List;

import de.zalando.zmon.fragment.ZmonDetailedAlertListFragment;
import de.zalando.zmon.persistence.Alert;
import de.zalando.zmon.persistence.AlertDetails;
import de.zalando.zmon.persistence.Team;
import de.zalando.zmon.task.GetActiveAlertsByIdTask;
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
                if (activeAlerts == null) {
                    Log.w("[zmon]", "Did not receive any alerts");
                } else {
                    Log.w("[zmon]", "Received " + activeAlerts.size() + " alerts");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertListFragment.setAlertDetailsByTeam(activeAlerts);
                        }
                    });
                }
            }
        }.execute(teamNames.toArray(new String[teamNames.size()]));

        new GetActiveAlertsByIdTask(this) {
            @Override
            protected void onPostExecute(final List<AlertDetails> activeAlerts) {
                if (activeAlerts == null) {
                    Log.w("[zmon]", "Did not receive any alerts for active alert query based on ids");
                } else {
                    Log.w("[zmon]", "Received " + activeAlerts.size() + " alerts by ids");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertListFragment.setAlertDetailsByAlertIds(activeAlerts);
                        }
                    });
                }
            }
        }.execute(getIdsOfSubscribedAlerts());
    }

    private String[] getIdsOfSubscribedAlerts() {
        List<Alert> subscribedAlerts = Alert.listAll(Alert.class);

        if (subscribedAlerts == null || subscribedAlerts.isEmpty()) {
            return null;
        }

        Collection<String> alertIds = Collections2.transform(subscribedAlerts, new Function<Alert, String>() {
            @Override
            public String apply(Alert input) {
                return input.getAlertDefinitionId();
            }
        });

        return alertIds.toArray(new String[alertIds.size()]);
    }

    @Override
    public void clickedAlert(AlertDetails alert) {
        startActivity(new AlertDetailActivity.AlertDetailActivityIntent(
                this,
                alert.getAlertDefinition().getAlertId()));
    }
}
