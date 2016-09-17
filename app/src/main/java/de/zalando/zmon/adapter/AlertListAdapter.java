package de.zalando.zmon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.persistence.Alert;

public class AlertListAdapter extends BaseListAdapter<Alert> {

    public AlertListAdapter(Context context, List<Alert> items) {
        super(context, items);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Alert item = getTypedItem(i);
        ViewHolder holder;
        if(view == null) {
            LayoutInflater inflater =  LayoutInflater.from(context);
            view = inflater.inflate(R.layout.listitem_alert, viewGroup, false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.team = (TextView) view.findViewById(R.id.team);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.name.setText(item.getAlertDefinitionId() + ": " + item.getName());
        holder.team.setText(item.getTeamName());

        return view;
    }

    private static class ViewHolder {
        TextView name;
        TextView team;
    }
}
