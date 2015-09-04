package de.zalando.zmon.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

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

    public void setTeams(final List<Team> teams) {
        final TeamListAdapter adapter = new TeamListAdapter(getActivity(), teams);
        teamList.setAdapter(adapter);

        final SwipeToDismissTouchListener<ListViewAdapter> touchListener = new SwipeToDismissTouchListener<>(new ListViewAdapter(teamList), new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
            @Override
            public boolean canDismiss(int i) {
                return true;
            }

            @Override
            public void onDismiss(ListViewAdapter listViewAdapter, int i) {
                Team team = (Team) adapter.getItem(i);

                teams.remove(team);
                teamList.setAdapter(new TeamListAdapter(getActivity(), teams));

                team.delete();
                Toast.makeText(getActivity(), R.string.notification_team_deleted, Toast.LENGTH_SHORT).show();
            }
        });

        teamList.setOnTouchListener(touchListener);
        teamList.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        teamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (touchListener.existPendingDismisses()) {
                    touchListener.undoPendingDismiss();
                } else {
                    Toast.makeText(getActivity(), "Position " + position, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fireNewTeam(String teamName) {
        if (callback != null) {
            callback.onCreateNewTeam(Team.of(teamName));
        }
    }
}
