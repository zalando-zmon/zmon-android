package de.zalando.zmon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.persistence.Entity;

public class EntitiesListAdapter extends BaseListAdapter<Entity> {

    private class ViewHolder {
        public TextView entity;
        public TextView captures;
        public TextView value;
    }

    public EntitiesListAdapter(Context context) {
        this(context, new ArrayList<Entity>());
    }

    public EntitiesListAdapter(Context context, List<Entity> items) {
        super(context, items);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Entity entity = getTypedItem(i);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listitem_entity, viewGroup, false);

            TextView entityView = (TextView) view.findViewById(R.id.entity);
            TextView capturesView = (TextView) view.findViewById(R.id.captures);
            TextView valueView = (TextView) view.findViewById(R.id.value);

            entityView.setText(entity.getName());
            capturesView.setText(buildCapturesJson(entity));
            valueView.setText(buildValueJson(entity));

            ViewHolder holder = new ViewHolder();
            holder.entity = entityView;
            holder.captures = capturesView;
            holder.value = valueView;
            view.setTag(holder);
        } else {
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.entity.setText(entity.getName());
            holder.captures.setText(buildCapturesJson(entity));
            holder.value.setText(buildValueJson(entity));
        }

        return view;
    }

    private String buildCapturesJson(Entity entity) {
        return entity.getCaptures().toString();
    }

    private String buildValueJson(Entity entity) {
        return entity.getValue().toString();
    }
}
