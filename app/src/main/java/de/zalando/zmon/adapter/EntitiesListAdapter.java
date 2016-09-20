package de.zalando.zmon.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.iweinzierl.jsonformat.HtmlFormatter;

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

        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater =  LayoutInflater.from (context);
            view = inflater.inflate(R.layout.listitem_entity, viewGroup, false);
            holder = new ViewHolder();
            holder.entity = (TextView) view.findViewById(R.id.entity);
            holder.captures = (TextView) view.findViewById(R.id.captures);
            holder.value = (TextView) view.findViewById(R.id.value);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.entity.setText(entity.getName());
        holder.captures.setText(Html.fromHtml(buildCapturesJson(entity)));
        holder.value.setText(Html.fromHtml(buildValueJson(entity)));

        return view;
    }

    private String buildCapturesJson(Entity entity) {
        return new HtmlFormatter().format(entity.getCaptures());
    }

    private String buildValueJson(Entity entity) {
        return new HtmlFormatter().format(entity.getValue());
    }
}
