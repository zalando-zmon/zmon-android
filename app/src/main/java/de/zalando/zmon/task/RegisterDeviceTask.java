package de.zalando.zmon.task;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Response;

import java.io.IOException;

import de.zalando.zmon.R;
import de.zalando.zmon.client.NotificationService;
import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.client.domain.DeviceSubscription;

public class RegisterDeviceTask extends HttpSafeAsyncTask<String, Void, Boolean> {

    public RegisterDeviceTask(Context context) {
        super(context);
    }

    @Override
    protected Boolean callSafe(String... deviceId) throws IOException {
        NotificationService service = ServiceFactory.createNotificationService(context);

        Response response = service
                .registerDevice(new DeviceSubscription(deviceId[0]))
                .execute()
                .raw();

        if (response.code() == 200) {
            Log.i("[rest]", "Successfully registered device '" + deviceId[0] + "' for push notifications");
            displayToastMessage(R.string.register_device_success_message);

            return true;
        } else {
            Log.w("[rest]", "Failed to register for push notifications; http status =" + response.code());
            displayToastMessage(R.string.register_device_error_message);

            return false;
        }
    }

    private void displayToastMessage(final int stringId) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(
                        context,
                        stringId,
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
