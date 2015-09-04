package de.zalando.zmon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.client.domain.ZmonAlertStatus;

public class AlertStatusListAdapter extends BaseAdapter {

    private static class ViewHolder {
        View itemView;
        TextView messageView;
    }

    private static class AlertStatusPriorityComparator implements Comparator<ZmonAlertStatus> {

        @Override
        public int compare(ZmonAlertStatus zmonAlertStatus, ZmonAlertStatus t1) {
            int prio1 = zmonAlertStatus.getAlertDefinition().getPriority();
            int prio2 = t1.getAlertDefinition().getPriority();

            if (prio1 < prio2) {
                return -1;
            } else if (prio1 == prio2) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    private Context context;
    private final List<ZmonAlertStatus> alertStatus;

    public AlertStatusListAdapter(Context context, List<ZmonAlertStatus> alertStatus) {
        this.context = context;
        this.alertStatus = new ArrayList<>(alertStatus);
        Collections.sort(this.alertStatus, new AlertStatusPriorityComparator());
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
            setBackgroundColor(view, alertStatus.getAlertDefinition().getPriority());

            TextView messageView = (TextView) view.findViewById(R.id.message);
            messageView.setText(alertStatus.getMessage());

            ViewHolder holder = new ViewHolder();
            holder.itemView = view;
            holder.messageView = messageView;

            view.setTag(holder);
        } else {
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.messageView.setText(alertStatus.getMessage());
            setBackgroundColor(holder.itemView, alertStatus.getAlertDefinition().getPriority());
        }

        return view;
    }

    private void setBackgroundColor(View container, int priority) {
        switch (priority) {
            case 1:
                container.setBackground(context.getDrawable(R.color.alert_critical));
                break;
            case 2:
                container.setBackground(context.getDrawable(R.color.alert_medium));
                break;
            default:
                container.setBackground(context.getDrawable(R.color.alert_low));
                break;
        }
    }
}
