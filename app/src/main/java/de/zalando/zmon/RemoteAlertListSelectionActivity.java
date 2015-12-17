package de.zalando.zmon;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import de.zalando.zmon.fragment.AlertHeadersListFragment;
import de.zalando.zmon.persistence.AlertHeader;
import de.zalando.zmon.task.GetAlertHeadersTask;

public class RemoteAlertListSelectionActivity extends BaseActivity implements AlertHeadersListFragment.Callback {

    private AlertHeadersListFragment alertHeadersListFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alert_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alertHeadersListFragment = new AlertHeadersListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.alert_list_fragment, alertHeadersListFragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        final ZmonApplication app = (ZmonApplication) getApplication();
        new GetAlertHeadersTask(app) {
            @Override
            protected void onPostExecute(List<AlertHeader> alertHeaders) {
                Log.i("[zmon]", "Received " + alertHeaders.size() + " alerts");
                alertHeadersListFragment.setAlertHeaders(alertHeaders);
            }
        }.execute();
    }

    @Override
    public void onAlertSelected(AlertHeader alertHeader) {
        startActivity(new AlertDetailActivity.AlertDetailActivityIntent(this, alertHeader.getAlertId()));
    }
}
