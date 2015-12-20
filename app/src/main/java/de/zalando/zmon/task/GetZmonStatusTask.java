package de.zalando.zmon.task;

import android.content.Context;

import java.io.IOException;

import de.zalando.zmon.ZmonStatusActivity;
import de.zalando.zmon.client.DataService;
import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.client.domain.ZmonStatus;

public class GetZmonStatusTask extends HttpSafeAsyncTask<Void, Void, ZmonStatus> {
    private ZmonStatusActivity zmonStatusActivity;

    protected GetZmonStatusTask(ZmonStatusActivity zmonStatusActivity, Context context) {
        super(context);
        this.zmonStatusActivity = zmonStatusActivity;
    }

    @Override
    protected ZmonStatus callSafe(Void... voids) throws IOException {
        final DataService dataService = ServiceFactory.createDataService(zmonStatusActivity);
        return dataService
                .getStatus()
                .execute()
                .body();
    }
}
