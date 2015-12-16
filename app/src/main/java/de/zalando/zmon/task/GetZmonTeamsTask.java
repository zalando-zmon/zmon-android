package de.zalando.zmon.task;

import java.util.List;

import de.zalando.zmon.ZmonApplication;
import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.client.ZmonService;

import android.os.AsyncTask;

public class GetZmonTeamsTask extends AsyncTask<Void, Void, List<String>> {

    public interface Callback {
        void onSuccess(List<String> teams);

        void onError(Exception e);
    }

    private final ZmonApplication zmonApplication;
    private final Callback callback;

    public GetZmonTeamsTask(final ZmonApplication zmonApplication, final Callback callback) {
        this.zmonApplication = zmonApplication;
        this.callback = callback;
    }

    @Override
    protected List<String> doInBackground(final Void... voids) {
        try {
            ZmonService zmonService = ServiceFactory.createZmonService(zmonApplication);
            return zmonService.listTeams();
        } catch (Exception e) {
            callback.onError(e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(final List<String> zmonTeams) {
        super.onPostExecute(zmonTeams);

        if (zmonTeams != null) {
            callback.onSuccess(zmonTeams);
        }
    }
}
