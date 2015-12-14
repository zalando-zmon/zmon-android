package de.zalando.zmon.client.profiler;

import android.util.Log;

import retrofit.Profiler;

public class LoggingProfiler implements Profiler {

    @Override
    public Object beforeCall() {
        return null;
    }

    @Override
    public void afterCall(RequestInformation info, long elapsedTime, int statusCode, Object beforeCallData) {
        Log.i("[rest]", String.format("=== %s   %s%s (%s)",
                info.getMethod(), info.getBaseUrl(), info.getRelativePath(), info.getContentLength()));
        Log.i("[rest]", "Request took " + elapsedTime + " ms");
        Log.i("[rest]", "Status Code = " + statusCode);
    }
}
