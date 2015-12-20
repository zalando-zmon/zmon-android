package de.zalando.zmon.task;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import de.zalando.zmon.client.DataService;
import de.zalando.zmon.client.NotificationService;
import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.client.domain.AlertDetails;
import de.zalando.zmon.persistence.Alert;

public class SyncAlertSubscriptionsTask extends HttpSafeAsyncTask<Void, Void, List<String>> {

    private final DataService dataService;
    private final NotificationService notificationService;

    public SyncAlertSubscriptionsTask(Context context) {
        super(context);
        dataService = ServiceFactory.createDataService(context);
        notificationService = ServiceFactory.createNotificationService(context);
    }

    @Override
    protected List<String> callSafe(final Void... voids) throws IOException {
        List<String> subscriptions = notificationService
                .listSubscriptions()
                .execute()
                .body();

        Log.d("[sync]", "Received " + subscriptions.size() + " subscriptions from server");

        removeOutdatedSubscriptions(subscriptions);

        Collection<String> newSubscriptions = filterForNewSubscriptions(subscriptions);
        Log.d("[sync]", "Found new subscriptions: " + newSubscriptions);

        for (String alertId : newSubscriptions) {
            addLocalSubscription(alertId);
        }

        return null;
    }

    private void removeOutdatedSubscriptions(List<String> subscriptions) {
        List<Alert> alerts = Alert.listAll(Alert.class);

        for (int index = alerts.size() - 1; index >= 0; index--) {
            Alert alert = alerts.get(index);

            if (!subscriptions.contains(alert.getAlertDefinitionId())) {
                alert.delete();

                Log.i("[sync]", "Deleted outdated local alert subscription: " + alert.getAlertDefinitionId());
            }
        }
    }

    private Collection<String> filterForNewSubscriptions(List<String> subscriptions) {
        final List<Alert> alerts = Alert.listAll(Alert.class);
        final Collection<String> localSubscriptions = Collections2.transform(alerts, new Function<Alert, String>() {
            @Override
            public String apply(@Nullable Alert input) {
                return input.getAlertDefinitionId();
            }
        });

        return Collections2.filter(subscriptions, new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String input) {
                return !localSubscriptions.contains(input);
            }
        });
    }

    private void addLocalSubscription(String alertId) throws IOException {
        AlertDetails alertDetails = dataService
                .getAlertDetails(alertId)
                .execute()
                .body();

        Alert alert = Alert.of(
                alertDetails.getAlertDefinition().getId(),
                alertDetails.getAlertDefinition().getName());

        alert.save();

        Log.i("[sync]", "Added new local alert subscription: " + alert.getAlertDefinitionId());
    }
}
