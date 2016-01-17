package de.zalando.zmon.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.common.base.Strings;

import java.io.IOException;

import de.zalando.zmon.LoginActivity;
import de.zalando.zmon.R;
import de.zalando.zmon.auth.Credentials;
import de.zalando.zmon.auth.CredentialsStore;
import de.zalando.zmon.client.OAuthAccessTokenService;
import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.client.exception.HttpException;
import retrofit.Call;
import retrofit.Response;

public abstract class HttpSafeAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    protected final Context context;

    protected HttpSafeAsyncTask(Context context) {
        this.context = context;
    }

    protected abstract Result callSafe(Params... params) throws IOException;

    protected Result doInBackground(Params... params) {
        try {
            return callSafe(params);
        } catch (Throwable e) {
            if (e instanceof HttpException) {
                HttpException ex = (HttpException) e;

                if (ex.getCode() == 401) {
                    try {
                        if (updateAuthToken()) {
                            callSafe(params);
                        } else {
                            displayHttpError(ex.getCode(), ex.getReason());
                        }
                    } catch (Throwable exc) {
                        handleException(exc);
                    }
                } else {
                    displayHttpError(ex.getCode(), ex.getReason());
                }
            } else if (e.getCause() instanceof HttpException) {
                HttpException ex = (HttpException) e.getCause();
                displayHttpError(ex.getCode(), ex.getReason());

                if (ex.getCode() == 401) {
                    try {
                        if (updateAuthToken()) {
                            callSafe(params);
                        } else {
                            displayHttpError(ex.getCode(), ex.getReason());
                        }
                    } catch (Throwable exc) {
                        handleException(exc);
                    }
                } else {
                    displayHttpError(ex.getCode(), ex.getReason());
                }
            } else {
                displayError("Unknown error occured: " + e.getMessage());
            }
        }

        return null;
    }

    private boolean updateAuthToken() throws IOException {
        CredentialsStore credentialsStore = new CredentialsStore(context);
        Credentials credentials = credentialsStore.getCredentials();

        if (credentials == null || Strings.isNullOrEmpty(credentials.getUsername()) || Strings.isNullOrEmpty(credentials.getPassword())) {
            return false;
        } else {
            String accessToken = getAccessToken();

            if (Strings.isNullOrEmpty(accessToken)) {
                Log.i("[rest]", "Did not receive new access token");
                return false;
            } else {
                Log.i("[rest]", "Successfully received access token: " + accessToken);
                credentialsStore.setAccessToken(accessToken);
                return true;
            }
        }
    }

    private String getAccessToken() throws IOException {
        final OAuthAccessTokenService OAuthAccessTokenService = ServiceFactory.createOAuthService(context);
        final Call<String> loginCall = OAuthAccessTokenService.login();
        final Response<String> loginResponse = loginCall.execute();

        if (loginResponse.code() >= 200 && loginResponse.code() < 300) {
            return loginResponse.body();
        } else {
            return null;
        }
    }

    private void handleException(Throwable e) {
        if (e instanceof HttpException) {
            HttpException ex = (HttpException) e;
            displayHttpError(ex.getCode(), ex.getReason());
        } else if (e.getCause() instanceof HttpException) {
            HttpException ex = (HttpException) e.getCause();
            displayHttpError(ex.getCode(), ex.getReason());
        } else {
            displayError("Unknown error occured: " + e.getMessage());
        }
    }

    private void displayHttpError(int code, String reason) {
        switch (code) {
            case 400:
                displayError(context.getString(R.string.error_400));
                break;
            case 401:
                displayError(context.getString(R.string.error_401));
                context.startActivity(new Intent(context, LoginActivity.class));
                break;
            case 403:
                displayError(context.getString(R.string.error_403));
                break;
            case 404:
                displayError(context.getString(R.string.error_404));
                break;
            case 408:
                displayError(context.getString(R.string.error_408));
                break;
            case 500:
                displayError(context.getString(R.string.error_500, reason));
                break;
            case 503:
                displayError(context.getString(R.string.error_503));
                break;
            default:
                displayError("Unknown error occured: http code = " + code + " , reason = " + reason);
                break;
        }
    }

    private void displayError(final String error) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
