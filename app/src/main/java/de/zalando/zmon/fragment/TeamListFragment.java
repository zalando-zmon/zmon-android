package de.zalando.zmon.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import de.zalando.zmon.R;
import de.zalando.zmon.adapter.TeamListAdapter;
import de.zalando.zmon.persistence.Team;

import java.util.List;

public class TeamListFragment extends Fragment {

    public interface Callback {
        void onObserveTeam(Team team);

        void onUnobserveTeam(Team team);
    }

    private SearchView teamSearch;

    private ListView teamList;

    private Callback callback;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_list, container, false);

        teamList = (ListView) view.findViewById(R.id.team_list);
        teamSearch = (SearchView) view.findViewById(R.id.team_search);

        return view;
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        if (activity instanceof Callback) {
            callback = (Callback) activity;
        }
    }

    public SearchView getTeamSearch() {
        return this.teamSearch;
    }

    public void setTeams(final List<Team> teams) {
        final TeamListAdapter adapter = new TeamListAdapter(getActivity(), teams, new TeamListAdapter.Callback() {
                    @Override
                    public void onTeamClicked(final Team team) {
                        if (team.isObserved()) {
                            callback.onUnobserveTeam(team);
                        } else {
                            callback.onObserveTeam(team);
                        }
                    }
                });
        teamList.setAdapter(adapter);
    }
}
