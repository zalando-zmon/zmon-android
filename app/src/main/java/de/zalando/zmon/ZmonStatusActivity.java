package de.zalando.zmon;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import de.zalando.zmon.client.ZmonStatusService;
import de.zalando.zmon.client.domain.ZmonStatus;

public class ZmonStatusActivity extends Activity {

    private TextView queueSize;
    private TextView activeWorkers;
    private TextView totalWorkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zmonstatus);

        queueSize = (TextView) findViewById(R.id.queue_size);
        activeWorkers = (TextView) findViewById(R.id.active_workers);
        totalWorkers = (TextView) findViewById(R.id.total_workers);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateZmonStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateZmonStatus();
    }

    private void updateZmonStatus() {
        new GetZmonStatusTask() {
            @Override
            protected void onPostExecute(ZmonStatus status) {
                super.onPostExecute(status);

                Log.d("[zmon]", "Zmon2 Total Workers  = " + status.getWorkersTotal());
                Log.d("[zmon]", "Zmon2 Active Workers = " + status.getWorkersActive());
                Log.d("[zmon]", "Zmon2 Workers        = " + status.getWorkers().size());
                Log.d("[zmon]", "Zmon2 Queue Size     = " + status.getQueueSize());
                Log.d("[zmon]", "Zmon2 Queues         = " + status.getQueues().size());

                queueSize.setText(Integer.toString(status.getQueueSize()));
                activeWorkers.setText(Integer.toString(status.getWorkersActive()));
                totalWorkers.setText(Integer.toString(status.getWorkersTotal()));
            }
        }.execute();
    }

    public class GetZmonStatusTask extends AsyncTask<Void, Void, ZmonStatus> {
        @Override
        protected ZmonStatus doInBackground(Void... voids) {
            final ZmonStatusService statusService = ((ZmonApplication) getApplication()).getZmonStatusService();
            return statusService.getStatus();
        }
    }
}
