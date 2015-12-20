package de.zalando.zmon.task;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.zalando.zmon.client.DataService;
import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.persistence.AlertDetails;
import de.zalando.zmon.util.EntityTransformator;

public class GetActiveAlertsByIdTask extends HttpSafeAsyncTask<String, Void, List<AlertDetails>> {

    private final DataService dataService;

    public GetActiveAlertsByIdTask(Context context) {
        super(context);
        dataService = ServiceFactory.createDataService(context);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List<AlertDetails> callSafe(String... alertIds) throws IOException {
        if (alertIds == null || alertIds.length == 0) {
            Log.d("[rest]", "Do not query for active alerts as no alertIds have been specified!");
            return Collections.EMPTY_LIST;
        }

        Log.d("[rest]", "get active alerts by ids: " + Arrays.toString(alertIds));

        List<AlertDetails> activeAlerts = new ArrayList<>();

        for (String alertId : alertIds) {
            AlertDetails alert = getActiveAlert(alertId);

            if (alert != null) {
                activeAlerts.add(alert);
            }
        }

        return activeAlerts;
    }

    private AlertDetails getActiveAlert(String alertId) throws IOException {
        de.zalando.zmon.client.domain.AlertDetails input = dataService
                .getAlertDetails(alertId)
                .execute()
                .body();

        if (input.getEntities() == null || input.getEntities().isEmpty()) {
            return null;
        } else {
            return EntityTransformator.transform(input);
        }
    }
}
