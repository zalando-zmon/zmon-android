package de.zalando.zmon.task;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import de.zalando.zmon.auth.Credentials;
import de.zalando.zmon.client.ServiceFactory;

public class GetAccessTokenTask extends HttpSafeAsyncTask<Credentials, Void, String> {

    public GetAccessTokenTask(Context context) {
        super(context);
    }

    @Override
    protected String callSafe(Credentials... credentials) throws IOException {
        if (credentials[0].getUsername() != null && credentials[0].getPassword() != null) {
            return ServiceFactory.createOAuthService(context)
                    .login()
                    .execute()
                    .body();
        } else {
            Log.w("[rest]", "Unable to get oauth access token because username/password are not set");
            return null;
        }
    }
}
