package de.zalando.zmon.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.zalando.zmon.R;
import de.zalando.zmon.client.domain.ZmonStatus;

public class ZmonStatusFragment extends Fragment {

    private static final String EXTRA_QUEUE_SIZE = "extra.queue.size";
    private static final String EXTRA_ACTIVE_WORKERS = "extra.active.workers";
    private static final String EXTRA_TOTAL_WORKERS = "extra.total.workers";

    private TextView queueSize;
    private TextView activeWorkers;
    private TextView totalWorkers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zmonstatus, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        queueSize = (TextView) view.findViewById(R.id.queue_size);
        activeWorkers = (TextView) view.findViewById(R.id.active_workers);
        totalWorkers = (TextView) view.findViewById(R.id.total_workers);

        if (savedInstanceState != null) {
            queueSize.setText(savedInstanceState.getString(EXTRA_QUEUE_SIZE));
            activeWorkers.setText(savedInstanceState.getString(EXTRA_ACTIVE_WORKERS));
            totalWorkers.setText(savedInstanceState.getString(EXTRA_TOTAL_WORKERS));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_QUEUE_SIZE, queueSize.getText().toString());
        outState.putString(EXTRA_ACTIVE_WORKERS, activeWorkers.getText().toString());
        outState.putString(EXTRA_TOTAL_WORKERS, totalWorkers.getText().toString());
    }

    public void update(ZmonStatus status) {
        updateZmonStatusNumbers(status);
        updateZmonStatusColors(status);
    }

    private void updateZmonStatusNumbers(ZmonStatus status) {
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
}
