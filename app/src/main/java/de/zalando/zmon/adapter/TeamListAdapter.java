package de.zalando.zmon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;

import de.zalando.zmon.R;
import de.zalando.zmon.persistence.Team;

public class TeamListAdapter extends BaseListAdapter<Team> {

    public TeamListAdapter(final Context context) {
        super(context, Collections.EMPTY_LIST);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Team team = (Team) getItem(i);

        ViewHolder holder;

        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.removable_listitem_team, viewGroup, false);

            holder = new ViewHolder();
            holder.teamName = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.teamName.setText(team.getName());

        return view;
    }

    public void remove(final int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView teamName;
    }
}
