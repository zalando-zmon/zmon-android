package de.zalando.zmon.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.common.base.Joiner;

import java.util.List;

import de.zalando.zmon.ZmonApplication;
import de.zalando.zmon.client.domain.ZmonAlertStatus;

public class GetZmonAlertsTask extends AsyncTask<String, Void, List<ZmonAlertStatus>> {

    public interface Callback {
        void onError(Exception e);

        void onResult(List<ZmonAlertStatus> alertStatusList);
    }

    private final ZmonApplication zmonApplication;
    private final Callback callback;

    public GetZmonAlertsTask(ZmonApplication zmonApplication, Callback callback) {
        this.zmonApplication = zmonApplication;
        this.callback = callback;
    }

    @Override
    protected List<ZmonAlertStatus> doInBackground(String... teams) {
        try {
            String teamQueryString = makeTeamString(teams);
            Log.d("zmon", "Query alerts for teams: " + teamQueryString);

            return zmonApplication.getZmonAlertsService().listByTeam(teamQueryString);
        } catch (Exception e) {
            Log.e("[zmon]", "Error while fetching alerts", e);
            callback.onError(e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<ZmonAlertStatus> alertStatusList) {
        super.onPostExecute(alertStatusList);
        callback.onResult(alertStatusList);
    }

    private String makeTeamString(String... teams) {
        return Joiner.on(",").join(teams);
    }
}
