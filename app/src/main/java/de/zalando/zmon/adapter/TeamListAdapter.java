package de.zalando.zmon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import de.zalando.zmon.client.domain.ZmonTeam;

public class TeamListAdapter extends BaseAdapter {

    private final Context context;

    private final List<ZmonTeam> teams;


    public TeamListAdapter(Context context, List<ZmonTeam> teams) {
        this.context = context;
        this.teams = teams;
    }

    @Override
    public int getCount() {
        return teams.size();
    }

    @Override
    public Object getItem(int i) {
        return teams.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ZmonTeam team = (ZmonTeam) getItem(i);

        TextView tv = new TextView(context);
        tv.setText(team.getName());

        return tv;
    }
}
