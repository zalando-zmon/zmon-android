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

            switch (response.getStatus()) {
                case 401:
                    return new UnauthorizedException(cause);

                default:
                    return new Exception(cause);
            }
        }

        return cause;
    }
}
