package de.zalando.zmon.task;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.persistence.AlertDetails;
import de.zalando.zmon.util.EntityTransformator;

public class GetAlertDetailsTask extends HttpSafeAsyncTask<String, Void, AlertDetails> {

    public GetAlertDetailsTask(Context context) {
        super(context);
    }

    @Override
    protected AlertDetails callSafe(String... params) throws IOException {
        Log.d("[rest]", "get alert details of alert: " + params[0]);

        de.zalando.zmon.client.domain.AlertDetails input = ServiceFactory.createDataService(context)
                .getAlertDetails(params[0])
                .execute()
                .body();

        return EntityTransformator.transform(input);
    }
}
