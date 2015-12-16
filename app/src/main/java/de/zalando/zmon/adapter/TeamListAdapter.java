package de.zalando.zmon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.persistence.Team;

public class TeamListAdapter extends BaseListAdapter<Team> {

    public TeamListAdapter(Context context, List<Team> items) {
        super(context, items);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Team team = (Team) getItem(i);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View itemView = inflater.inflate(R.layout.listitem_team, viewGroup, false);
        final TextView name = (TextView) itemView.findViewById(R.id.name);
        name.setText(team.getName());

        return itemView;
    }
}
