package de.zalando.zmon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import de.zalando.zmon.client.domain.ZmonAlertStatus;

public class AlertStatusListAdapter extends BaseAdapter {

    private Context context;
    private final List<ZmonAlertStatus> alertStatus;

    public AlertStatusListAdapter(Context context, List<ZmonAlertStatus> alertStatus) {
        this.context = context;
        this.alertStatus = alertStatus;
    }

    @Override
    public int getCount() {
        return alertStatus.size();
    }

    @Override
    public Object getItem(int i) {
        return alertStatus.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ZmonAlertStatus alertStatus = (ZmonAlertStatus) getItem(i);

        // TODO proper implementation
        TextView tv = new TextView(context);
        tv.setText(alertStatus.getMessage());

        return tv;
    }
}
