package de.zalando.zmon.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected final Context context;
    protected List<T> items;

    public BaseListAdapter(final Context context, final List<T> items) {
        super();
        this.context = context;
        this.items = new ArrayList<>(items);
    }

    public BaseListAdapter(final Context context, final List<T> items, Comparator<T> comparator) {
        super();
        this.context = context;
        this.items = new ArrayList<>(items);

        Collections.sort(this.items, comparator);
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
}
