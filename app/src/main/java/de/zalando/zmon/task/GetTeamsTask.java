package de.zalando.zmon.task;

import android.content.Context;
import android.util.Log;

import java.util.List;

import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.util.HttpSafeAsyncTask;

public class GetTeamsTask extends HttpSafeAsyncTask<Void, Void, List<String>> {

    public GetTeamsTask(Context context) {
        super(context);
    }

    @Override
    protected List<String> callSafe(final Void... voids) {
        Log.d("[rest]", "list all teams registered in zmon");
        return ServiceFactory.createZmonService(context).listTeams();
    }
}
