package de.zalando.zmon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.common.base.Strings;

import java.util.List;

import de.zalando.zmon.fragment.AlertListFragment;
import de.zalando.zmon.persistence.Alert;
import de.zalando.zmon.task.UnregisterAlertTask;

public class ObservedAlertsActivity extends BaseActivity implements AlertListFragment.Callback {

    private AlertListFragment alertListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alertListFragment = new AlertListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, alertListFragment)
                .commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        Log.d("[zmon]", "Update UI with all local alerts");

        new AsyncTask<Void, Void, List<Alert>>() {
            @Override
            protected List<Alert> doInBackground(Void... voids) {
                return Alert.listAll(Alert.class);
            }

            @Override
            protected void onPostExecute(List<Alert> alerts) {
                super.onPostExecute(alerts);
                alertListFragment.setAlerts(alerts);
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_alert_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_alert:
                startActivity(new Intent(this, RemoteAlertListSelectionActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_observed_alerts;
    }

    @Override
    public void onAlertClicked(Alert alert) {
        if (alert != null && !Strings.isNullOrEmpty(alert.getAlertDefinitionId())) {
            startActivity(new AlertDetailActivity.AlertDetailActivityIntent(this, alert.getAlertDefinitionId()));
        }
    }

    @Override
    public void onAlertRemoved(Alert alert) {
        if (alert != null && !Strings.isNullOrEmpty(alert.getAlertDefinitionId())) {
            new UnregisterAlertTask(this).execute(alert.getAlertDefinitionId());
        }
    }
}
