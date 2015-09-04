package de.zalando.zmon;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import de.zalando.zmon.client.ZmonStatusService;
import de.zalando.zmon.client.domain.ZmonStatus;
import de.zalando.zmon.fragment.ZmonStatusFragment;

public class ZmonStatusActivity extends SelfUpdatableActivity {

    private ZmonStatusFragment zmonStatusFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zmonstatus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        zmonStatusFragment = new ZmonStatusFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, zmonStatusFragment)
                .commit();
    }

    @Override
    protected void runJob() {
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
