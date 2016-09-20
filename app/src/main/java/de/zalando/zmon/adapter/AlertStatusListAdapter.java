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
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater =  LayoutInflater.from(context);
            view = inflater.inflate(R.layout.listitem_zmon_alert, viewGroup, false);

            holder = new ViewHolder();
            holder.itemView = view;
            holder.alertName = (TextView) view.findViewById(R.id.name);
            holder.teamName = (TextView) view.findViewById(R.id.team);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.alertName.setText(alertHeader.getName());
        holder.teamName.setText(alertHeader.getTeam());

        return view;
    }
}
