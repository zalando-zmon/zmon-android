package de.zalando.zmon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.persistence.AlertHeader;

public class AlertStatusListAdapter extends BaseListAdapter<AlertHeader> {

    private static class ViewHolder {

        View itemView;
        TextView alertName;
        TextView teamName;
    }

    public AlertStatusListAdapter(Context context, List<AlertHeader> items) {
        super(context, items);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AlertHeader alertHeader = getTypedItem(i);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = layoutInflater.inflate(R.layout.listitem_zmon_alert, viewGroup, false);

            TextView alertName = (TextView) view.findViewById(R.id.name);
            alertName.setText(alertHeader.getName());

            TextView teamView = (TextView) view.findViewById(R.id.team);
            teamView.setText(alertHeader.getTeam());

            ViewHolder holder = new ViewHolder();
            holder.itemView = view;
            holder.alertName = alertName;
            holder.teamName = teamView;

            view.setTag(holder);
        } else {
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.alertName.setText(alertHeader.getName());
            holder.teamName.setText(alertHeader.getTeam());
        }

        return view;
    }
}
