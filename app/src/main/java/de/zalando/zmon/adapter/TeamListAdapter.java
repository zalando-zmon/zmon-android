package de.zalando.zmon.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.persistence.Team;

public class TeamListAdapter extends BaseAdapter {

    public interface Callback {
        void onTeamClicked(Team team);
    }

    private final Context context;

    private final List<Team> teams;

    private final Callback callback;


    public TeamListAdapter(Context context, List<Team> teams, Callback callback) {
        this.context = context;
        this.teams = teams;
        this.callback = callback;
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
        final Team team = (Team) getItem(i);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View itemView = inflater.inflate(R.layout.listitem_team, viewGroup, false);
        final TextView name = (TextView) itemView.findViewById(R.id.name);
        name.setText(team.getName());

        final ImageView observeButton = (ImageView) itemView.findViewById(R.id.observe);
        setObservedImage(observeButton, team.isObserved());

        observeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("zmon", "clicked team: " + team);
                callback.onTeamClicked(team);
                setObservedImage(observeButton, team.isObserved());
            }
        });

        return itemView;
    }

    private void setObservedImage(ImageView observeButton, boolean isObserved) {
        Resources resources = context.getResources();
        Resources.Theme theme = context.getTheme();

        Drawable image;

        if (isObserved) {
            image = resources.getDrawable(R.drawable.star_orange, theme);
        } else {
            image = resources.getDrawable(R.drawable.star_gray, theme);
        }

        observeButton.setImageDrawable(image);
    }
}
