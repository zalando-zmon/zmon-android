package de.zalando.zmon.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected final Context context;
    protected List<T> items;

    public BaseListAdapter(final Context context) {
        super();
        this.context = context;
        this.items = new ArrayList<>();
    }

    public BaseListAdapter(final Context context, final List<T> items) {
        super();
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(final int i) {
        return items.get(i);
    }

    public T getTypedItem(final int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(final int i) {
        return i;
    }

    public void setItems(final List<T> items) {
        this.items = items;
        notifyDataSetChanged();
        notifyDataSetInvalidated();
    }
}
