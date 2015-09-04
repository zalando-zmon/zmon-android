package de.zalando.zmon;

import android.os.AsyncTask;
import android.os.Bundle;

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
    protected void onResume() {
        super.onResume();

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

    private static class GetZmonAlertsTask extends AsyncTask<String, Void, List<ZmonAlertStatus>> {

        private final ZmonApplication zmonApplication;

        private GetZmonAlertsTask(ZmonApplication zmonApplication) {
            this.zmonApplication = zmonApplication;
        }

        @Override
        protected List<ZmonAlertStatus> doInBackground(String... teams) {
            return zmonApplication.getZmonAlertsService().listByTeam(teams[0]);
        }
    }
}
