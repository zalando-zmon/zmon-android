package de.zalando.zmon.task;

import android.content.Context;
import android.util.Log;

import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.persistence.AlertDetails;
import de.zalando.zmon.util.EntityTransformator;

public class GetAlertDetailsTask extends HttpSafeAsyncTask<String, Void, AlertDetails> {

    public GetAlertDetailsTask(Context context) {
        super(context);
    }

    @Override
    protected AlertDetails callSafe(String... params) {
        Log.d("[rest]", "get alert details of alert: " + params[0]);

        de.zalando.zmon.client.domain.AlertDetails input =
                ServiceFactory.createZmonService(context).getAlertDetails(params[0]);

        return EntityTransformator.transform(input);
    }
}
