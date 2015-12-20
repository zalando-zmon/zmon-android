package de.zalando.zmon.task;

import android.content.Context;
import android.util.Log;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.persistence.AlertHeader;

public class GetAlertHeadersTask extends HttpSafeAsyncTask<Void, Void, List<AlertHeader>> {

    public GetAlertHeadersTask(Context context) {
        super(context);
    }

    @Override
    protected List<AlertHeader> callSafe(Void... params) throws IOException {
        Log.d("[rest]", "list all alert headers");

        List<de.zalando.zmon.client.domain.AlertHeader> alertHeaders = ServiceFactory.createDataService(context)
                .listAlertHeaders()
                .execute()
                .body();

        return Lists.transform(alertHeaders, new Function<de.zalando.zmon.client.domain.AlertHeader, AlertHeader>() {
            @Override
            public AlertHeader apply(de.zalando.zmon.client.domain.AlertHeader input) {
                return new AlertHeader(input.getId(), input.getName(), input.getTeam(), input.getResponsibleTeam());
            }
        });
    }
}
