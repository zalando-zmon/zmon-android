package de.zalando.zmon.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.adapter.AlertListAdapter;
import de.zalando.zmon.adapter.BaseListAdapter;
import de.zalando.zmon.persistence.Alert;

public class AlertListFragment extends Fragment {

    public interface Callback {
        void onAlertClicked(Alert alert);
    }

    private Callback callback;

    private ListView alertList;
    private BaseListAdapter<Alert> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alert_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new AlertListAdapter(getActivity(), null);

        alertList = (ListView) view.findViewById(R.id.alert_list);
        alertList.setAdapter(adapter);
        alertList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });

        setupSwipeToDismissListener();
    }

    @Override
    public void onStart() {
        super.onStart();

        Activity activity = getActivity();

        if (activity instanceof Callback) {
            callback = (Callback) activity;
        }
    }

    @SuppressWarnings("unchecked")
    private void setupSwipeToDismissListener() {
        Log.d("[zmon]", "Setup SwipeToDismiss listener");

        final BaseListAdapter<Alert> adapter = (BaseListAdapter<Alert>) alertList.getAdapter();
        final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new ListViewAdapter(alertList),
                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListViewAdapter view, int position) {
                                Alert alert = adapter.getTypedItem(position);
                                alert.delete();

                                adapter.remove(position);
                            }
                        });

        alertList.setOnTouchListener(touchListener);
        alertList.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        alertList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (touchListener.existPendingDismisses()) {
                    touchListener.undoPendingDismiss();
                } else if (callback != null) {
                    callback.onAlertClicked(adapter.getTypedItem(position));
                }
            }
        });
    }

    public void setAlerts(List<Alert> alerts) {
        adapter.setItems(alerts);
    }
}
