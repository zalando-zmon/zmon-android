package de.zalando.zmon.fragment;

import de.zalando.zmon.R;
import de.zalando.zmon.client.domain.ZmonStatus;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.TextView;

public class ZmonStatusFragment extends Fragment {

    private static final String EXTRA_QUEUE_SIZE = "extra.queue.size";
    private static final String EXTRA_ACTIVE_WORKERS = "extra.active.workers";
    private static final String EXTRA_TOTAL_WORKERS = "extra.total.workers";

    private TextView queueSize;
    private TextView activeWorkers;
    private TextView totalWorkers;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zmonstatus, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        queueSize = (TextView) view.findViewById(R.id.queue_size);
        activeWorkers = (TextView) view.findViewById(R.id.active_workers);
        totalWorkers = (TextView) view.findViewById(R.id.total_workers);
        progressBar = (ProgressBar) view.findViewById(R.id.workers_progress_bar);

        if (savedInstanceState != null) {
            queueSize.setText(savedInstanceState.getString(EXTRA_QUEUE_SIZE));

            float activeWorkersText = Float.valueOf(savedInstanceState.getString(EXTRA_ACTIVE_WORKERS));
            float totalWorkersText = Float.valueOf(savedInstanceState.getString(EXTRA_TOTAL_WORKERS));
            activeWorkers.setText(String.valueOf(Math.round(activeWorkersText)));
            totalWorkers.setText(String.valueOf(Math.round(totalWorkersText)));
            setWorkerProgressBarProgress(activeWorkersText, totalWorkersText);
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_QUEUE_SIZE, queueSize.getText().toString());
        outState.putString(EXTRA_ACTIVE_WORKERS, activeWorkers.getText().toString());
        outState.putString(EXTRA_TOTAL_WORKERS, totalWorkers.getText().toString());
    }

    public void update(final ZmonStatus status) {
        updateZmonStatusNumbers(status);
        updateZmonStatusColors(status);
    }

    private void setWorkerProgressBarProgress(final float active, final float total) {

        int result = Math.round((active / total) * 100);

        progressBar.setProgress(result);
    }

    private void updateZmonStatusNumbers(final ZmonStatus status) {
        queueSize.setText(Integer.toString(status.getQueueSize()));

        float workersActive = status.getWorkersActive();
        float workersTotal = status.getWorkersTotal();
        activeWorkers.setText(String.valueOf(workersActive));
        totalWorkers.setText(String.valueOf(workersTotal));
        setWorkerProgressBarProgress(workersActive, workersTotal);
    }

    private void updateZmonStatusColors(final ZmonStatus status) {
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
}
