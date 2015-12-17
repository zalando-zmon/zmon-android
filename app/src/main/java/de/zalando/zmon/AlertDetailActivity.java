package de.zalando.zmon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.common.base.Strings;

import de.zalando.zmon.fragment.AlertDetailFragment;
import de.zalando.zmon.persistence.AlertDetails;
import de.zalando.zmon.task.GetAlertDetailsTask;
import de.zalando.zmon.task.RegisterAlertTask;

public class AlertDetailActivity extends BaseActivity {

    private static final String EXTRA_ALERT_ID = "extra.alert.id";

    public static class AlertDetailActivityIntent extends Intent {
        public AlertDetailActivityIntent(Context context, String alertId) {
            super(context, AlertDetailActivity.class);
            putExtra(EXTRA_ALERT_ID, alertId);
        }
    }

    private AlertDetailFragment alertDetailFragment;
    private String alertId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alertDetailFragment = new AlertDetailFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.alert_detail_fragment, alertDetailFragment)
                .commit();

        extractAndSetAlertId();
    }

    @Override
    protected void onStart() {
        super.onStart();

        new GetAlertDetailsTask(this) {
            @Override
            public void onPostExecute(AlertDetails alertDetails) {
                alertDetailFragment.setAlertDetails(alertDetails);
            }
        }.execute(alertId);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_alert_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.monitor_alert:
                startMonitoring();
                return true;
        }

        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alert_detail;
    }

    private void extractAndSetAlertId() {
        String alertId = getIntent().getStringExtra(EXTRA_ALERT_ID);

        if (Strings.isNullOrEmpty(alertId)) {
            Toast.makeText(
                    this,
                    getString(R.string.alertdetail_error_invalid_alert_id, alertId),
                    Toast.LENGTH_LONG)
                    .show();

            finish();
        }

        this.alertId = alertId;
    }

    private void startMonitoring() {
        new RegisterAlertTask(this).execute(alertId);
    }
}
