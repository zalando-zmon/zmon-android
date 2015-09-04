package de.zalando.zmon.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.adapter.TeamListAdapter;
import de.zalando.zmon.persistence.Team;

public class TeamListFragment extends Fragment {

    public interface Callback {
        void onCreateNewTeam(Team team);
    }

    private ListView teamList;
    private EditText newTeamField;

    private Callback callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_list, container, false);

        teamList = (ListView) view.findViewById(R.id.team_list);
        newTeamField = (EditText) view.findViewById(R.id.new_team);

        Button addTeamBtn = (Button) view.findViewById(R.id.add_team_button);
        addTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireNewTeam(newTeamField.getText().toString());
                newTeamField.setText("");
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof Callback) {
            callback = (Callback) activity;
        }
    }

    public void setTeams(List<Team> teams) {
        teamList.setAdapter(new TeamListAdapter(getActivity(), teams));
    }

    private void fireNewTeam(String teamName) {
        if (callback != null) {
            callback.onCreateNewTeam(Team.of(teamName));
        }
    }
}
