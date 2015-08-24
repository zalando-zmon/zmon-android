package de.zalando.zmon;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import de.zalando.zmon.client.ZmonStatusService;
import de.zalando.zmon.client.domain.ZmonStatus;

public class ZmonStatusActivity extends AppCompatActivity {

    private static final String EXTRA_IS_STATUS_UPDATER_PAUSED = "extra.is.status.updater.paused";
    private static final String EXTRA_QUEUE_SIZE = "extra.queue.size";
    private static final String EXTRA_ACTIVE_WORKERS = "extra.active.workers";
    private static final String EXTRA_TOTAL_WORKERS = "extra.total.workers";

    private MenuItem pauseItem;
    private MenuItem resumeItem;

    private TextView queueSize;
    private TextView activeWorkers;
    private TextView totalWorkers;

    private ScheduledThreadPoolExecutor statusUpdateExecutor = new ScheduledThreadPoolExecutor(5);
    private boolean isStatusUpdateExecutorPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zmonstatus);

        queueSize = (TextView) findViewById(R.id.queue_size);
        activeWorkers = (TextView) findViewById(R.id.active_workers);
        totalWorkers = (TextView) findViewById(R.id.total_workers);

        if (savedInstanceState != null) {
            isStatusUpdateExecutorPaused = savedInstanceState.getBoolean(EXTRA_IS_STATUS_UPDATER_PAUSED, false);
            queueSize.setText(savedInstanceState.getString(EXTRA_QUEUE_SIZE));
            activeWorkers.setText(savedInstanceState.getString(EXTRA_ACTIVE_WORKERS));
            totalWorkers.setText(savedInstanceState.getString(EXTRA_TOTAL_WORKERS));
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        pauseItem = menu.findItem(R.id.pause);
        resumeItem = menu.findItem(R.id.resume);

        pauseItem.setVisible(!isStatusUpdateExecutorPaused);
        resumeItem.setVisible(isStatusUpdateExecutorPaused);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_zmonstatus, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startStatusUpdateExecutor();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shutdownStatusUpdateExecutor();
    }

    @Override
    protected void onStop() {
        super.onStop();
        shutdownStatusUpdateExecutor();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_IS_STATUS_UPDATER_PAUSED, isStatusUpdateExecutorPaused);
        outState.putString(EXTRA_QUEUE_SIZE, queueSize.getText().toString());
        outState.putString(EXTRA_ACTIVE_WORKERS, activeWorkers.getText().toString());
        outState.putString(EXTRA_TOTAL_WORKERS, totalWorkers.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pause:
                pauseStatusUpdaterExecutor();
                return true;

            case R.id.resume:
                resumeStatusUpdaterExecutor();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startStatusUpdateExecutor() {
        if (statusUpdateExecutor != null) {
            statusUpdateExecutor.shutdownNow();
        }

        if (!isStatusUpdateExecutorPaused) {
            Log.d("[zmon]", "Start zmon status updater");

            statusUpdateExecutor = new ScheduledThreadPoolExecutor(1);
            statusUpdateExecutor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    updateZmonStatus();
                }
            }, 0, 5000, TimeUnit.MILLISECONDS);
        }
    }

    private void pauseStatusUpdaterExecutor() {
        Log.d("[zmon]", "Pause zmon status updater");
        isStatusUpdateExecutorPaused = true;
        pauseItem.setVisible(false);
        resumeItem.setVisible(true);
        shutdownStatusUpdateExecutor();
    }

    private void resumeStatusUpdaterExecutor() {
        Log.d("[zmon]", "Resume zmon status updater");
        isStatusUpdateExecutorPaused = false;
        pauseItem.setVisible(true);
        resumeItem.setVisible(false);
        startStatusUpdateExecutor();
    }

    private void shutdownStatusUpdateExecutor() {
        if (statusUpdateExecutor != null) {
            Log.d("[zmon]", "Stop zmon status updater");
            statusUpdateExecutor.shutdown();
            statusUpdateExecutor = null;
        }
    }

    private void updateZmonStatus() {
        Log.d("[zmon]", "Start process to update zmon2 status");

        new GetZmonStatusTask() {
            @Override
            protected void onPostExecute(ZmonStatus status) {
                super.onPostExecute(status);

                if (status != null) {
                    updateZmonStatusNumbers(status);
                    updateZmonStatusColors(status);
                }
            }
        }.execute();
    }

    private void updateZmonStatusNumbers(ZmonStatus status) {
        Log.d("[zmon]", "Zmon2 Total Workers  = " + status.getWorkersTotal());
        Log.d("[zmon]", "Zmon2 Active Workers = " + status.getWorkersActive());
        Log.d("[zmon]", "Zmon2 Workers        = " + status.getWorkers().size());
        Log.d("[zmon]", "Zmon2 Queue Size     = " + status.getQueueSize());
        Log.d("[zmon]", "Zmon2 Queues         = " + status.getQueues().size());

        queueSize.setText(Integer.toString(status.getQueueSize()));
        activeWorkers.setText(Integer.toString(status.getWorkersActive()));
        totalWorkers.setText(Integer.toString(status.getWorkersTotal()));
    }

    private void updateZmonStatusColors(ZmonStatus status) {
        if (status.getQueueSize() > 1000) {
            queueSize.setTextColor(getResources().getColor(R.color.status_warning));
        } else {
            queueSize.setTextColor(getResources().getColor(R.color.status_ok));
        }

        if (status.getWorkersActive() < status.getWorkersTotal()) {
            activeWorkers.setTextColor(getResources().getColor(R.color.status_warning));
            totalWorkers.setTextColor(getResources().getColor(R.color.status_warning));
        } else {
            activeWorkers.setTextColor(getResources().getColor(R.color.status_ok));
            totalWorkers.setTextColor(getResources().getColor(R.color.status_ok));
        }
    }

    private void displayError(final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(ZmonStatusActivity.this)
                        .setTitle(R.string.error_network)
                        .setMessage(e.getMessage())
                        .show();
            }
        });
    }

    public class GetZmonStatusTask extends AsyncTask<Void, Void, ZmonStatus> {
        @Override
        protected ZmonStatus doInBackground(Void... voids) {
            try {
                final ZmonStatusService statusService = ((ZmonApplication) getApplication()).getZmonStatusService();
                return statusService.getStatus();
            } catch (Exception e) {
                Log.e("[zmon]", "Error while fetching zmon2 status", e);
                displayError(e);
            }

            return null;
        }
    }
}
