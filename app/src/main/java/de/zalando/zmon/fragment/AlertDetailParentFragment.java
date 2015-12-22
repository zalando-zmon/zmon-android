package de.zalando.zmon.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.zalando.zmon.R;
import de.zalando.zmon.persistence.AlertDetails;

public class AlertDetailParentFragment extends Fragment {

    private AlertDetailFragment alertDetailFragment;
    private AlertDetailFragment alertEntitiesFragment;

    private View detailsHeader;
    private View entitiesHeader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert_detail_parent, container, false);

        detailsHeader = view.findViewById(R.id.container_details);
        entitiesHeader = view.findViewById(R.id.container_entities);

        detailsHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetails();
            }
        });

        entitiesHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEntities();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        alertDetailFragment = (AlertDetailFragment) getFragmentManager().findFragmentByTag("fragment_alert_details");
        alertEntitiesFragment = (AlertDetailFragment) getFragmentManager().findFragmentByTag("fragment_alert_entities");

        showDetails();
    }

    public void setAlertDetails(AlertDetails alertDetails) {
        alertDetailFragment.setAlertDetails(alertDetails);
        // TODO set entities on entities fragment
    }

    public void showDetails() {
        entitiesHeader.findViewById(R.id.indicator_entities).setVisibility(View.INVISIBLE);
        detailsHeader.findViewById(R.id.indicator_details).setVisibility(View.VISIBLE);

        alertDetailFragment.getView().setVisibility(View.GONE);
    }

    public void showEntities() {
        detailsHeader.findViewById(R.id.indicator_details).setVisibility(View.INVISIBLE);
        entitiesHeader.findViewById(R.id.indicator_entities).setVisibility(View.VISIBLE);

        alertEntitiesFragment.getView().setVisibility(View.VISIBLE);
    }
}
