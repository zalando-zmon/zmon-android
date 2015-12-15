package de.zalando.zmon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import de.zalando.zmon.client.domain.ZmonAlertStatus;
import de.zalando.zmon.fragment.AlertDetailFragment;
import de.zalando.zmon.task.GetZmonAlertTask;
import de.zalando.zmon.task.RegisterAlertTask;

public class AlertDetailActivity extends BaseActivity {

    private static final String EXTRA_ALERT_ID = "extra.alert.id";

    public static class AlertDetailActivityIntent extends Intent {
        public AlertDetailActivityIntent(Context context, int alertId) {
            super(context, AlertDetailActivity.class);
            putExtra(EXTRA_ALERT_ID, alertId);
        }
    }

    private AlertDetailFragment alertDetailFragment;
    private int alertId;

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

        ZmonApplication app = (ZmonApplication) getApplication();
        new GetZmonAlertTask(app, new GetZmonAlertTask.Callback() {
            @Override
            public void onError(Exception e) {
                Toast.makeText(
                        AlertDetailActivity.this,
                        getString(R.string.alertdetail_error_load_alert_failed, e.getMessage()),
                        Toast.LENGTH_LONG)
                        .show();

                finish();
            }

            @Override
            public void onResult(List<ZmonAlertStatus> alertStatus) {
                alertDetailFragment.setAlertDetails(alertStatus.get(0));
            }
        }).execute((long) alertId);
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
        int alertId = getIntent().getIntExtra(EXTRA_ALERT_ID, -1);

        if (alertId <= 0) {
            Toast.makeText(
                    this,
                    getString(R.string.alertdetail_error_invalid_alert_id, String.valueOf(alertId)),
                    Toast.LENGTH_LONG)
                    .show();

            finish();
        }

        this.alertId = alertId;
    }

    private void startMonitoring() {
        new RegisterAlertTask(this).execute((long) alertId);
    }
}
