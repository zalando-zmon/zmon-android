package de.zalando.zmon.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Comparator;
import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.persistence.AlertDetails;
import de.zalando.zmon.persistence.Entity;

public class AlertDetailsListAdapter extends BaseListAdapter<AlertDetails> {

    private static class ViewHolder {
        View itemView;
        TextView messageView;
        TextView teamView;
        TextView startTimeView;
    }

    private static class AlertStatusPriorityComparator implements Comparator<AlertDetails> {

        @Override
        public int compare(AlertDetails a1, AlertDetails a2) {
            int prio1 = a1.getAlertDefinition().getPriority();
            int prio2 = a2.getAlertDefinition().getPriority();

            if (prio1 < prio2) {
                return -1;
            } else if (prio1 == prio2) {
                return a1.getEntities().get(0).getStartTime().getTime() < a2.getEntities().get(0).getStartTime().getTime()
                        ? 1
                        : -1;
            } else {
                return 1;
            }
        }
    }

    public AlertDetailsListAdapter(Context context, List<AlertDetails> alertDetails) {
        super(context, alertDetails, new AlertStatusPriorityComparator());
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

            TextView startTimeView = (TextView) view.findViewById(R.id.start_time);
            startTimeView.setText(formatStartTime(alertDetails.getEntities()));

            TextView teamView = (TextView) view.findViewById(R.id.team);
            teamView.setText(alertDetails.getAlertDefinition().getTeam());

            ViewHolder holder = new ViewHolder();
            holder.itemView = view;
            holder.messageView = messageView;
            holder.teamView = teamView;
            holder.startTimeView = startTimeView;

            view.setTag(holder);
        } else {
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.messageView.setText(alertDetails.getMessage());
            holder.startTimeView.setText(formatStartTime(alertDetails.getEntities()));
            holder.teamView.setText(alertDetails.getAlertDefinition().getTeam());
            setBackgroundColor(holder.itemView, alertDetails.getAlertDefinition().getPriority());
        }

        return view;
    }

    private String formatStartTime(List<Entity> entities) {
        if (entities == null) {
            Log.w("[zmon]", "got alert but not entity");
            return "";
        }

        long start = entities.get(0).getStartTime().getTime() * 1000;
        long delta = System.currentTimeMillis() - start;

        long secondsSinceStart = delta / 1000;
        long minutesSinceStart = secondsSinceStart / 60;
        long hoursSinceStart = minutesSinceStart / 60;
        long daysSinceStart = hoursSinceStart / 24;
        long yearsSinceStart = daysSinceStart / 365;

        if (secondsSinceStart < 60) {
            return String.valueOf(secondsSinceStart) + "s";
        } else if (minutesSinceStart < 60) {
            return String.valueOf(minutesSinceStart) + "m";
        } else if (hoursSinceStart < 24) {
            return String.valueOf(hoursSinceStart) + "h";
        } else if (daysSinceStart < 365) {
            return String.valueOf(daysSinceStart) + "d";
        } else {
            return String.valueOf(yearsSinceStart) + "y";
        }
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
