package de.zalando.zmon.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import de.zalando.zmon.adapter.AlertStatusListAdapter;
import de.zalando.zmon.client.domain.ZmonAlertStatus;

public class AlertListFragment extends Fragment {

    public interface Callback {
        void onAlertSelected(ZmonAlertStatus alert);
    }

    private Callback callback;

    private ListView alertList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alert_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alertList = (ListView) view.findViewById(R.id.alert_list);
        alertList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ZmonAlertStatus alertStatus = (ZmonAlertStatus) adapterView.getAdapter().getItem(i);
                String text = String.format(
                        getString(R.string.dialog_text_really_observe_alert),
                        alertStatus.getAlertDefinition().getName());

                DialogInterface.OnClickListener closeListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                };

                DialogInterface.OnClickListener submitListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (callback != null) {
                            callback.onAlertSelected(alertStatus);
                        }

                        dialogInterface.dismiss();
                    }
                };

                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.dialog_title_really_observe_alert)
                        .setMessage(text)
                        .setNegativeButton(R.string.dialog_button_cancel_observe_alert, closeListener)
                        .setPositiveButton(R.string.dialog_button_submit_observe_alert, submitListener)
                        .create()
                        .show();
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
        alertList.setAdapter(new AlertStatusListAdapter(getActivity(), alertStatusList));
    }
}
