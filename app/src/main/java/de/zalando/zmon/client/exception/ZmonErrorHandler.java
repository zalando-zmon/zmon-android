package de.zalando.zmon.client.exception;

import android.util.Log;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ZmonErrorHandler implements ErrorHandler {

    @Override
    public Throwable handleError(RetrofitError cause) {
        Response response = cause.getResponse();
        if (response != null) {
            Log.d("[zmon]", "Received response code: " + response.getStatus());
            return new HttpException(response.getStatus(), response.getReason());
        }

        return cause;
    }
}
