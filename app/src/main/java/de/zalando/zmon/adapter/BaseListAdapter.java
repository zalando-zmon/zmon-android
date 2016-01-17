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

    protected Comparator<T> comparator;

    @SuppressWarnings("unchecked")
    public BaseListAdapter(final Context context, final List<T> items) {
        super();
        this.context = context;
        this.items = items != null ? new ArrayList<>(items) : (List<T>) Collections.EMPTY_LIST;
    }

    @SuppressWarnings("unchecked")
    public BaseListAdapter(final Context context, final List<T> items, Comparator<T> comparator) {
        super();
        this.context = context;
        this.items = items != null ? new ArrayList<>(items) : (List<T>) Collections.EMPTY_LIST;
        this.comparator = comparator;

        try {
            Collections.sort(this.items, comparator);
        }
        catch (IllegalArgumentException iae) {
            // iae can be thrown for unknown reason
        }
    }

    @SuppressWarnings("unchecked")
    public void setItems(List<T> items) {
        this.items = items != null ? new ArrayList<>(items) : (List<T>) Collections.EMPTY_LIST;

        if (comparator != null) {
            try {
                Collections.sort(this.items, comparator);
            } catch (IllegalArgumentException iae) {
                // iae can be thrown for unknown reason
            }
        }

        notifyDataSetChanged();
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

    public void remove(final int i) {
        if (i < getCount()) {
            items.remove(i);
            notifyDataSetChanged();
        }
    }
}
