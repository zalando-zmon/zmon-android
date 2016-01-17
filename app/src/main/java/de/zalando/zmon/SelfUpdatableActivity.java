package de.zalando.zmon;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class SelfUpdatableActivity extends BaseActivity {

    private static final String EXTRA_IS_STATUS_UPDATER_PAUSED = "extra.is.status.updater.paused";
    public static final int PERIOD = 15000;

    private MenuItem pauseItem;
    private MenuItem resumeItem;

    private ScheduledThreadPoolExecutor statusUpdateExecutor = new ScheduledThreadPoolExecutor(5);
    private boolean isStatusUpdateExecutorPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            isStatusUpdateExecutorPaused = savedInstanceState.getBoolean(EXTRA_IS_STATUS_UPDATER_PAUSED, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_base, menu);
        return super.onCreateOptionsMenu(menu);
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
    protected void onResume() {
        super.onResume();
        resumeStatusUpdaterExecutor();
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
            Log.d("[zmon]", "Start update job");

            statusUpdateExecutor = new ScheduledThreadPoolExecutor(1);
            statusUpdateExecutor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    runJob();
                }
            }, 0, PERIOD, TimeUnit.MILLISECONDS);
        }
    }

    private void pauseStatusUpdaterExecutor() {
        Log.d("[zmon]", "Pause update job");

        isStatusUpdateExecutorPaused = true;

        if (pauseItem != null) {
            pauseItem.setVisible(false);
        }

        if (resumeItem != null) {
            resumeItem.setVisible(true);
        }

        shutdownStatusUpdateExecutor();
    }

    private void resumeStatusUpdaterExecutor() {
        Log.d("[zmon]", "Resume update job");

        isStatusUpdateExecutorPaused = false;

        if (pauseItem != null) {
            pauseItem.setVisible(true);
        }

        if (resumeItem != null) {
            resumeItem.setVisible(false);
        }

        startStatusUpdateExecutor();
    }

    private void shutdownStatusUpdateExecutor() {
        if (statusUpdateExecutor != null) {
            Log.d("[zmon]", "Stop update job");

            statusUpdateExecutor.shutdown();
            statusUpdateExecutor = null;
        }
    }

    protected abstract void runJob();
}
