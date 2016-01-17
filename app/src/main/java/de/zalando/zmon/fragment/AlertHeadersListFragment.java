package de.zalando.zmon.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.adapter.AlertStatusListAdapter;
import de.zalando.zmon.persistence.AlertHeader;

public class AlertHeadersListFragment extends Fragment {

    public interface Callback {
        void onAlertSelected(AlertHeader alert);
    }

    private Callback callback;

    private ListView alertList;
    private AlertStatusListAdapter alertListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alert_status_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alertListAdapter = new AlertStatusListAdapter(getActivity(), Collections.EMPTY_LIST);
        alertListAdapter.setComparator(new Comparator<AlertHeader>() {
            @Override
            public int compare(AlertHeader lhs, AlertHeader rhs) {
                return lhs.getAlertId().compareTo(rhs.getAlertId());
            }
        });

        alertList = (ListView) view.findViewById(R.id.alert_list);
        alertList.setAdapter(alertListAdapter);
        alertList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (callback != null) {
                    AlertHeader alertHeader = (AlertHeader) adapterView.getAdapter().getItem(i);
                    callback.onAlertSelected(alertHeader);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        Activity activity = getActivity();
        if (activity instanceof Callback) {
            callback = (Callback) activity;
        }
    }

    public void setAlertHeaders(List<AlertHeader> alertHeaders) {
        alertListAdapter.setItems(alertHeaders);
    }
}
