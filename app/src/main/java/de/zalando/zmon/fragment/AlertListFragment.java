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
import de.zalando.zmon.adapter.AlertListAdapter;
import de.zalando.zmon.persistence.Alert;

public class AlertListFragment extends Fragment {

    private ListView alertList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alert_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alertList = (ListView) view.findViewById(R.id.alert_list);
    }

    public void setAlerts(List<Alert> alerts) {
        alertList.setAdapter(new AlertListAdapter(getActivity(), alerts));
    }
}
