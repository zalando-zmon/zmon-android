package de.zalando.zmon.task;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import de.zalando.zmon.client.ServiceFactory;

public class GetTeamsTask extends HttpSafeAsyncTask<Void, Void, List<String>> {

    public GetTeamsTask(Context context) {
        super(context);
    }

    @Override
    protected List<String> callSafe(final Void... voids) throws IOException {
        Log.d("[rest]", "list all teams registered in zmon");
        return ServiceFactory.createDataService(context)
                .listTeams()
                .execute()
                .body();
    }
}
