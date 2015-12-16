package de.zalando.zmon;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import de.zalando.zmon.client.domain.ZmonAlertStatus;
import de.zalando.zmon.fragment.AlertStatusListFragment;
import de.zalando.zmon.task.GetZmonAlertsTask;
import de.zalando.zmon.task.RegisterAlertTask;

public class RemoteAlertListSelectionActivity extends BaseActivity implements AlertStatusListFragment.Callback {

    private AlertStatusListFragment alertStatusListFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alert_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alertStatusListFragment = new AlertStatusListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.alert_list_fragment, alertStatusListFragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        final ZmonApplication app = (ZmonApplication) getApplication();
        new GetZmonAlertsTask(app, new GetZmonAlertsTask.Callback() {
            @Override
            public void onError(Exception e) {
                Log.e("[zmon]", "Exception while fetching all alerts", e);
            }

            @Override
            public void onResult(List<ZmonAlertStatus> alertStatusList) {
                Log.i("[zmon]", "Received " + alertStatusList.size() + " alerts");
                alertStatusListFragment.setZmonAlertStatus(alertStatusList);
            }
        }).execute();
    }

    @Override
    public void onAlertSelected(ZmonAlertStatus alert) {
        Log.d("[zmon]", "Selected alert " + alert.getAlertDefinition().getName() + " for monitoring");
        new RegisterAlertTask(this).execute((long) alert.getAlertDefinition().getId());
    }
}
