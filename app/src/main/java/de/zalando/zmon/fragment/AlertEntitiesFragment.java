package de.zalando.zmon.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.adapter.EntitiesListAdapter;
import de.zalando.zmon.persistence.Entity;

public class AlertEntitiesFragment extends Fragment {

    private EntitiesListAdapter entitiesListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert_entities, container, false);

        entitiesListAdapter = new EntitiesListAdapter(getActivity());

        ListView entitiesList = (ListView) view.findViewById(R.id.entities_list);
        entitiesList.setAdapter(entitiesListAdapter);

        return view;
    }

    public void setAlertEntities(List<Entity> entities) {
        if (entities != null) {
            entitiesListAdapter.setItems(entities);
        } else {
            entitiesListAdapter.setItems(new ArrayList<Entity>(0));
        }
    }
}
