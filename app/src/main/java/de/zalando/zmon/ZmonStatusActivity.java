package de.zalando.zmon;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import de.zalando.zmon.client.ZmonStatusService;
import de.zalando.zmon.client.domain.ZmonStatus;
import de.zalando.zmon.fragment.ZmonStatusFragment;
import de.zalando.zmon.navigation.NavigationClickListener;
import de.zalando.zmon.navigation.NavigationItemAdapter;

public class ZmonStatusActivity extends AppCompatActivity {

    private static final String EXTRA_IS_STATUS_UPDATER_PAUSED = "extra.is.status.updater.paused";

    private ZmonStatusFragment zmonStatusFragment;

    private MenuItem pauseItem;
    private MenuItem resumeItem;

    private ScheduledThreadPoolExecutor statusUpdateExecutor = new ScheduledThreadPoolExecutor(5);
    private boolean isStatusUpdateExecutorPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zmonstatus);

        if (savedInstanceState != null) {
            isStatusUpdateExecutorPaused = savedInstanceState.getBoolean(EXTRA_IS_STATUS_UPDATER_PAUSED, false);
        }

        ListView navigationList = (ListView) findViewById(R.id.navigation_list);
        navigationList.setAdapter(new NavigationItemAdapter(this));
        navigationList.setOnItemClickListener(new NavigationClickListener(this));

        zmonStatusFragment = new ZmonStatusFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, zmonStatusFragment).commit();
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
                    Log.d("[zmon]", "Zmon2 Total Workers  = " + status.getWorkersTotal());
                    Log.d("[zmon]", "Zmon2 Active Workers = " + status.getWorkersActive());
                    Log.d("[zmon]", "Zmon2 Workers        = " + status.getWorkers().size());
                    Log.d("[zmon]", "Zmon2 Queue Size     = " + status.getQueueSize());
                    Log.d("[zmon]", "Zmon2 Queues         = " + status.getQueues().size());

                    zmonStatusFragment.update(status);
                } else {
                    Log.w("[zmon]", "No zmon2 status received");
                }
            }
        }.execute();
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
