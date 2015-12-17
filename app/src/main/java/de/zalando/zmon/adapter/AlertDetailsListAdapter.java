package de.zalando.zmon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Comparator;
import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.client.domain.ZmonAlertStatus;
import de.zalando.zmon.persistence.AlertDetails;

public class AlertDetailsListAdapter extends BaseListAdapter<AlertDetails> {

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

    public AlertDetailsListAdapter(Context context, List<AlertDetails> alertDetails) {
        super(context, alertDetails);
        // TODO sort
        //Collections.sort(this.alertStatus, new AlertStatusPriorityComparator());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AlertDetails alertDetails = getTypedItem(i);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = layoutInflater.inflate(R.layout.listitem_detailed_zmon_alert, viewGroup, false);
            setBackgroundColor(view, alertDetails.getAlertDefinition().getPriority());

            TextView messageView = (TextView) view.findViewById(R.id.message);
            messageView.setText(alertDetails.getMessage());

            ViewHolder holder = new ViewHolder();
            holder.itemView = view;
            holder.messageView = messageView;

            view.setTag(holder);
        } else {
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.messageView.setText(alertDetails.getMessage());
            setBackgroundColor(holder.itemView, alertDetails.getAlertDefinition().getPriority());
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
