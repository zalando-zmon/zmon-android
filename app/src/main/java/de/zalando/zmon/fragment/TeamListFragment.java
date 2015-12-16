package de.zalando.zmon.fragment;

import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.adapter.TeamListAdapter;
import de.zalando.zmon.persistence.Team;

import android.app.Activity;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;

public class TeamListFragment extends Fragment {

    public interface Callback {
        void onTeamSelected(Team team);
    }

    private ListView teamList;

    private Callback callback;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_list, container, false);

        teamList = (ListView) view.findViewById(R.id.team_list);
        teamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                    Team team = (Team) adapterView.getAdapter().getItem(i);

                    if (callback != null) {
                        callback.onTeamSelected(team);
                    }
                }
            });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Activity activity = getActivity();

        if (activity instanceof Callback) {
            callback = (Callback) activity;
        }
    }

    public void setTeams(final List<Team> teams) {
        final TeamListAdapter adapter = new TeamListAdapter(getActivity(), teams);

        teamList.setAdapter(adapter);
    }

}
