package de.zalando.zmon.adapter;

import android.content.Context;
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
        View itemView = View.inflate(context, R.layout.listitem_alert, null);

        TextView nameView = (TextView) itemView.findViewById(R.id.name);
        TextView teamView = (TextView) itemView.findViewById(R.id.team);

        nameView.setText(item.getAlertDefinitionId() + ": " + item.getName());
        teamView.setText(item.getTeamName());

        return itemView;
    }
}
