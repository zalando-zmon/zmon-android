package de.zalando.zmon.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.zalando.zmon.R;
import de.zalando.zmon.persistence.AlertDetails;

public class AlertDetailFragment extends Fragment {

    private TextView description;
    private TextView team;
    private TextView responsibleTeam;
    private TextView technicalDetails;
    private TextView checkDetails;
    private TextView checkTeam;
    private TextView checkInterval;
    private TextView checkEntities;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alert_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        description = (TextView) view.findViewById(R.id.description);
        team = (TextView) view.findViewById(R.id.team);
        responsibleTeam = (TextView) view.findViewById(R.id.responsible_team);
        technicalDetails = (TextView) view.findViewById(R.id.technical_details);
        checkDetails = (TextView) view.findViewById(R.id.check_details);
        checkTeam = (TextView) view.findViewById(R.id.check_team);
        checkInterval = (TextView) view.findViewById(R.id.check_interval);
        checkEntities = (TextView) view.findViewById(R.id.check_entities);
    }

    public void setAlertDetails(AlertDetails alert) {
        description.setText(alert.getAlertDefinition().getDescription());
        team.setText(alert.getAlertDefinition().getTeam());
        responsibleTeam.setText(alert.getAlertDefinition().getResponsibleTeam());
    }
}
