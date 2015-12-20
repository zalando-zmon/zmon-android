package de.zalando.zmon.task;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import de.zalando.zmon.client.ServiceFactory;

public class GetAlertSubscriptionsTask extends HttpSafeAsyncTask<Void, Void, List<String>> {

    public GetAlertSubscriptionsTask(Context context) {
        super(context);
    }

    @Override
    protected List<String> callSafe(Void... params) throws IOException {
        Log.d("[rest]", "list all active alerts");
        return ServiceFactory.createNotificationService(context)
                .listSubscriptions()
                .execute()
                .body();
    }
}
