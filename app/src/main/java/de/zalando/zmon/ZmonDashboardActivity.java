package de.zalando.zmon;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.List;

import de.zalando.zmon.client.domain.ZmonAlertStatus;
import de.zalando.zmon.fragment.ZmonAlertListFragment;

public class ZmonDashboardActivity extends BaseActivity {

    private ZmonAlertListFragment alertListFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alertListFragment = new ZmonAlertListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.alert_list_fragment, alertListFragment)
                .commit();
    }

    @Override
    protected void runJob() {
        new GetZmonAlertsTask((ZmonApplication) getApplication()) {
            @Override
            protected void onPostExecute(final List<ZmonAlertStatus> zmonAlertStatuses) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertListFragment.setZmonAlertStatus(zmonAlertStatuses);
                    }
                });
            }
        }.execute("PayProc & Tooling/Payment Processing/Backend");
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

    private class GetZmonAlertsTask extends AsyncTask<String, Void, List<ZmonAlertStatus>> {

        private final ZmonApplication zmonApplication;

        private GetZmonAlertsTask(ZmonApplication zmonApplication) {
            this.zmonApplication = zmonApplication;
        }

        @Override
        protected List<ZmonAlertStatus> doInBackground(String... teams) {
            try {
                return zmonApplication.getZmonAlertsService().listByTeam(teams[0]);
            } catch (Exception e) {
                Log.e("[zmon]", "Error while fetching alerts", e);
                displayError(e);
            }

            return null;
        }
    }
}
