package de.zalando.zmon.task;

import android.content.Context;
import android.util.Log;

import com.google.common.base.Joiner;

import java.util.List;

import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.client.domain.ZmonAlertStatus;
import de.zalando.zmon.util.HttpSafeAsyncTask;

public class GetZmonAlertsTask extends HttpSafeAsyncTask<String, Void, List<ZmonAlertStatus>> {

    public GetZmonAlertsTask(Context context) {
        super(context);
    }

    @Override
    protected List<ZmonAlertStatus> callSafe(String... teams) {
        if (teams != null && teams.length != 0) {
            String teamQueryString = makeTeamString(teams);
            Log.d("zmon", "Query alerts for teams: " + teamQueryString);

            return ServiceFactory.createZmonService(context).listAlerts(teamQueryString);
        } else {
            Log.d("zmon", "Query all alerts");
            return ServiceFactory.createZmonService(context).listAlerts();
        }
    }

    private String makeTeamString(String... teams) {
        return Joiner.on(",").join(teams);
    }
}
