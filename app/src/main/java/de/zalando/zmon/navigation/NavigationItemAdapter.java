package de.zalando.zmon.navigation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.zalando.zmon.R;

public class NavigationItemAdapter extends BaseAdapter {

    private static final int[] navigationItems = {
            R.string.nav_zmon_status,
            R.string.nav_dashboard
    };

    private final Context context;

    public NavigationItemAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return navigationItems.length;
    }

    @Override
    public Object getItem(int i) {
        return navigationItems[i];
    }

    @Override
    public long getItemId(int i) {
        return navigationItems[i];
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int item = (int) getItem(i);

        TextView tv = new TextView(context);
        tv.setText(context.getResources().getString(item));

        return tv;
    }
}
