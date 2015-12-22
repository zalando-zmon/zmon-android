package de.zalando.zmon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.common.base.Strings;

import de.zalando.zmon.fragment.AlertDetailFragment;
import de.zalando.zmon.fragment.AlertEntitiesFragment;
import de.zalando.zmon.persistence.AlertDetails;
import de.zalando.zmon.task.GetAlertDetailsTask;
import de.zalando.zmon.task.RegisterAlertTask;

public class AlertDetailActivity extends BaseActivity {

    private static final String EXTRA_ALERT_ID = "extra.alert.id";
    private AlertDetails alertDetails;

    public static class AlertDetailActivityIntent extends Intent {
        public AlertDetailActivityIntent(Context context, String alertId) {
            super(context, AlertDetailActivity.class);
            putExtra(EXTRA_ALERT_ID, alertId);
        }
    }

    private AlertDetailFragment alertDetailFragment;
    private AlertEntitiesFragment alertEntitiesFragment;

    private FrameLayout alertDetailsFrame;
    private FrameLayout alertEntitiesFrame;

    private View header;
    private View detailsHeader;
    private View entitiesHeader;
    private String alertId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alertDetailFragment = new AlertDetailFragment();
        alertEntitiesFragment = new AlertEntitiesFragment();

        header = findViewById(R.id.nav_header);
        detailsHeader = findViewById(R.id.container_details);
        entitiesHeader = findViewById(R.id.container_entities);

        alertDetailsFrame = (FrameLayout) findViewById(R.id.alert_details_fragment);
        alertEntitiesFrame = (FrameLayout) findViewById(R.id.alert_entities_fragment);

        detailsHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetails();
            }
        });

        entitiesHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEntities();
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.alert_details_fragment, alertDetailFragment)
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.alert_entities_fragment, alertEntitiesFragment)
                .commit();

        extractAndSetAlertId();
    }

    @Override
    protected void onStart() {
        super.onStart();

        showDetails();

        new GetAlertDetailsTask(this) {
            @Override
            public void onPostExecute(AlertDetails alertDetails) {
                if (alertDetails != null && alertDetails.getAlertDefinition() != null) {
                    setTitle(alertDetails.getAlertDefinition().getName());
                }

                setAlertDetails(alertDetails);
                updateUI();
            }
        }.execute(alertId);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateUI();
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

    public void showDetails() {
        entitiesHeader.findViewById(R.id.indicator_entities).setVisibility(View.INVISIBLE);
        alertEntitiesFrame.setVisibility(View.GONE);
        detailsHeader.findViewById(R.id.indicator_details).setVisibility(View.VISIBLE);
        alertDetailsFrame.setVisibility(View.VISIBLE);

        int color = getResources().getColor(R.color.colorPrimaryDark);
        header.setBackgroundColor(color);

        updateUI();
    }

    public void showEntities() {
        detailsHeader.findViewById(R.id.indicator_details).setVisibility(View.INVISIBLE);
        alertDetailsFrame.setVisibility(View.GONE);
        entitiesHeader.findViewById(R.id.indicator_entities).setVisibility(View.VISIBLE);
        alertEntitiesFrame.setVisibility(View.VISIBLE);

        int color = getResources().getColor(R.color.alert_critical);
        header.setBackgroundColor(color);

        updateUI();
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

    private void setAlertDetails(AlertDetails alertDetails) {
        this.alertDetails = alertDetails;
    }

    private void updateUI() {
        if (alertDetails != null) {
            alertDetailFragment.setAlertDetails(alertDetails);
            alertEntitiesFragment.setAlertEntities(alertDetails.getEntities());
        }
    }

    private void startMonitoring() {
        new RegisterAlertTask(this).execute(alertId);
    }
}
