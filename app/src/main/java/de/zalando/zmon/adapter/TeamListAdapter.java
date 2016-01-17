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
    public View getView(final int i, final View view, final ViewGroup viewGroup) {
        final Team team = (Team) getItem(i);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View itemView = inflater.inflate(R.layout.removable_listitem_team, viewGroup, false);
        final TextView name = (TextView) itemView.findViewById(R.id.name);
        name.setText(team.getName());

        return itemView;
    }

    public void remove(final int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView dataTextView;

        ViewHolder(final View view) {
            dataTextView = ((TextView) view.findViewById(R.id.name));
            view.setTag(this);
        }
    }
}
