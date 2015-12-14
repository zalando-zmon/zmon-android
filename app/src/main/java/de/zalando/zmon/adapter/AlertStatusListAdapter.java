package de.zalando.zmon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.client.domain.ZmonAlertStatus;

public class AlertStatusListAdapter extends BaseAdapter {

    private static class ViewHolder {
        View itemView;
        TextView alertName;
        TextView teamName;
    }

    private Context context;
    private final List<ZmonAlertStatus> alertStatus;

    public AlertStatusListAdapter(Context context, List<ZmonAlertStatus> alertStatus) {
        this.context = context;
        this.alertStatus = new ArrayList<>(alertStatus);
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

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = layoutInflater.inflate(R.layout.listitem_zmon_alert, viewGroup, false);

            TextView alertName = (TextView) view.findViewById(R.id.name);
            alertName.setText(alertStatus.getAlertDefinition().getName());

            TextView teamView = (TextView) view.findViewById(R.id.team);
            teamView.setText(alertStatus.getAlertDefinition().getTeam());

            ViewHolder holder = new ViewHolder();
            holder.itemView = view;
            holder.alertName = alertName;
            holder.teamName = teamView;

            view.setTag(holder);
        } else {
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.alertName.setText(alertStatus.getAlertDefinition().getName());
            holder.teamName.setText(alertStatus.getAlertDefinition().getTeam());
        }

        return view;
    }
}
