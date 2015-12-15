package de.zalando.zmon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        TextView idView = (TextView) itemView.findViewById(R.id.id);
        TextView nameView = (TextView) itemView.findViewById(R.id.name);
        TextView dateView = (TextView) itemView.findViewById(R.id.date);

        idView.setText(String.valueOf(item.getId()));
        nameView.setText(item.getName());
        dateView.setText(item.getLastModified().toString());

        return itemView;
    }
}
