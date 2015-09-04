package de.zalando.zmon.navigation;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import de.zalando.zmon.ObservedTeamsActivity;
import de.zalando.zmon.R;
import de.zalando.zmon.ZmonDashboardActivity;
import de.zalando.zmon.ZmonStatusActivity;

public class NavigationClickListener implements AdapterView.OnItemClickListener {

    private final Context context;

    public NavigationClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int item = (int) adapterView.getAdapter().getItem(i);

        switch (item) {
            case R.string.nav_zmon_status:
                context.startActivity(new Intent(context, ZmonStatusActivity.class));
                return;

            case R.string.nav_dashboard:
                context.startActivity(new Intent(context, ZmonDashboardActivity.class));
                return;
            case R.string.nav_observed_teams:
                context.startActivity(new Intent(context, ObservedTeamsActivity.class));
                return;
        }

        Log.w("[zmon]", "Unknown navigation item: " + item);
    }
}
