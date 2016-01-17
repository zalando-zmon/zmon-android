package de.zalando.zmon.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

import java.util.Comparator;
import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.adapter.TeamListAdapter;
import de.zalando.zmon.persistence.Team;

public class RemovableTeamListFragment extends Fragment {

    private TeamListAdapter teamListAdapter;

    private ListView teamList;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_list, container, false);

        teamListAdapter = new TeamListAdapter(getActivity());
        teamListAdapter.setComparator(new Comparator<Team>() {
            @Override
            public int compare(Team lhs, Team rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });

        teamList = (ListView) view.findViewById(R.id.team_list);
        teamList.setAdapter(teamListAdapter);
        teamList.setEmptyView(view.findViewById(R.id.empty_text));

        final SwipeToDismissTouchListener<ListViewAdapter> touchListener = new SwipeToDismissTouchListener<>(
                new ListViewAdapter(teamList), new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
            @Override
            public boolean canDismiss(final int position) {
                return true;
            }

            @Override
            public void onDismiss(final ListViewAdapter view, final int position) {
                Team team = (Team) teamList.getAdapter().getItem(position);
                ((TeamListAdapter) teamList.getAdapter()).remove(position);

                // Remove it also as observed team
                team.delete();
            }
        });

        teamList.setOnTouchListener(touchListener);
        teamList.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());

        teamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position,
                                    final long id) {
                if (touchListener.existPendingDismisses()) {
                    touchListener.undoPendingDismiss();
                } else {

                    // Toast.makeText(getContext(), "Position " + position, LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void setTeams(final List<Team> teams) {
        teamListAdapter.setItems(teams);
    }
}
