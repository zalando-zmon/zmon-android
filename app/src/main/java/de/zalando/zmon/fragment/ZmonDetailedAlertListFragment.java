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

import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.adapter.AlertStatusDetailListAdapter;
import de.zalando.zmon.client.domain.ZmonAlertStatus;

public class ZmonDetailedAlertListFragment extends Fragment {

    public interface Callback {
        void clickedAlert(ZmonAlertStatus alert);
    }

    private ListView alertList;
    private Callback callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alert_status_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alertList = (ListView) view.findViewById(R.id.alert_list);
        alertList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ZmonAlertStatus alert = (ZmonAlertStatus) adapterView.getAdapter().getItem(i);
                if (callback != null) {
                    callback.clickedAlert(alert);
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

    public void setZmonAlertStatus(List<ZmonAlertStatus> alertStatusList) {
        alertList.setAdapter(new AlertStatusDetailListAdapter(getActivity(), alertStatusList));
    }
}
