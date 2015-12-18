package de.zalando.zmon.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import de.zalando.zmon.LoginActivity;
import de.zalando.zmon.R;
import de.zalando.zmon.client.exception.HttpException;

public abstract class HttpSafeAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    protected final Context context;

    protected HttpSafeAsyncTask(Context context) {
        this.context = context;
    }

    protected abstract Result callSafe(Params... params);

    protected Result doInBackground(Params... params) {
        try {
            return callSafe(params);
        } catch (Throwable e) {
            if (e.getCause() instanceof HttpException) {
                HttpException ex = (HttpException) e.getCause();
                displayHttpError(ex.getCode(), ex.getReason());
            } else {
                displayError("Unknown error occured: " + e.getMessage());
            }
        }

        return null;
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
