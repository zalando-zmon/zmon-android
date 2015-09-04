package de.zalando.zmon.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.adapter.TeamListAdapter;
import de.zalando.zmon.client.domain.ZmonTeam;

public class TeamListFragment extends Fragment {

    private ListView teamList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_list, container, false);

        teamList = (ListView) view.findViewById(R.id.team_list);

        return view;
    }

    public void setTeams(List<ZmonTeam> teams) {
        teamList.setAdapter(new TeamListAdapter(getActivity(), teams));
    }
}
