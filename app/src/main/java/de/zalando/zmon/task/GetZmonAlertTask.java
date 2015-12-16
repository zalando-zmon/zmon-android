package de.zalando.zmon.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import de.zalando.zmon.ZmonApplication;
import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.client.domain.ZmonAlertStatus;

public class GetZmonAlertTask extends AsyncTask<Long, Void, List<ZmonAlertStatus>> {

    public interface Callback {
        void onError(Exception e);

        void onResult(List<ZmonAlertStatus> alertStatus);
    }

    private final ZmonApplication zmonApplication;
    private final Callback callback;

    public GetZmonAlertTask(ZmonApplication zmonApplication, Callback callback) {
        this.zmonApplication = zmonApplication;
        this.callback = callback;
    }

    @Override
    protected List<ZmonAlertStatus> doInBackground(Long... ids) {
        try {
            Log.d("zmon", "Get alert by id: " + ids[0]);
            return ServiceFactory.createZmonService(zmonApplication).getAlert(ids[0]);
        } catch (Exception e) {
            Log.e("[zmon]", "Error while fetching alerts", e);
            callback.onError(e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<ZmonAlertStatus> alertStatus) {
        super.onPostExecute(alertStatus);
        callback.onResult(alertStatus);
    }
}
