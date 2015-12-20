package de.zalando.zmon.client.interceptor;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import de.zalando.zmon.client.exception.HttpException;

public class ErrorHandlerInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        if (response != null && response.code() >= 400) {
            Log.d("[zmon]", "Received response code: " + response.code());
            throw new HttpException(response.code(), response.message());
        }

        return response;
    }
}
