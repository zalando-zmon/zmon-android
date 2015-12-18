package de.zalando.zmon.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;

import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.persistence.AlertDetails;
import de.zalando.zmon.util.EntityTransformator;

public class GetActiveAlertsTask extends HttpSafeAsyncTask<String, Void, List<AlertDetails>> {

    public GetActiveAlertsTask(Context context) {
        super(context);
    }

    @Override
    protected List<AlertDetails> callSafe(String... teams) {
        List<de.zalando.zmon.client.domain.AlertDetails> alertDetails;

        if (teams != null && teams.length != 0) {
            String teamQueryString = makeTeamString(teams);
            Log.d("[rest]", "list active alerts by teams: " + teamQueryString);
            alertDetails = ServiceFactory.createZmonService(context).getActiveAlerts(teamQueryString);
        } else {
            Log.d("[rest]", "list all active alerts");
            alertDetails = ServiceFactory.createZmonService(context).getActiveAlerts();
        }

        if (alertDetails == null) {
            Log.d("[rest]", "Received no active alerts: null");
            return null;
        } else {
            Log.d("[rest]", "Received " + alertDetails.size() + " active alerts");
            return map(alertDetails);
        }
    }

    @NonNull
    private List<AlertDetails> map(List<de.zalando.zmon.client.domain.AlertDetails> activeAlerts) {
        return Lists.transform(activeAlerts, new Function<de.zalando.zmon.client.domain.AlertDetails, AlertDetails>() {
            @Override
            public AlertDetails apply(de.zalando.zmon.client.domain.AlertDetails input) {
                return EntityTransformator.transform(input);
            }
        });
    }

    private String makeTeamString(String... teams) {
        return Joiner.on(",").join(teams);
    }
}
