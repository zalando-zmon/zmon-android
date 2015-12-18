package de.zalando.zmon.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import de.zalando.zmon.R;
import de.zalando.zmon.persistence.AlertDetails;

public class AlertDetailFragment extends Fragment {

    private TextView description;
    private TextView team;
    private TextView responsibleTeam;
    private View priority;
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
        priority = view.findViewById(R.id.priority);
        checkDetails = (TextView) view.findViewById(R.id.check_details);
        checkTeam = (TextView) view.findViewById(R.id.check_team);
        checkInterval = (TextView) view.findViewById(R.id.check_interval);
        checkEntities = (TextView) view.findViewById(R.id.check_entities);
    }

    public void setAlertDetails(AlertDetails alert) {
        if (alert == null || alert.getAlertDefinition() == null) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        } else {
            description.setText(alert.getAlertDefinition().getDescription());
            team.setText(alert.getAlertDefinition().getTeam());
            responsibleTeam.setText(alert.getAlertDefinition().getResponsibleTeam());
            setPriorityColor(alert.getAlertDefinition().getPriority());
        }
    }

    private void setPriorityColor(int priority) {
        Resources resources = getResources();
        switch (priority) {
            case 1:
                this.priority.setBackgroundColor(resources.getColor(R.color.alert_critical));
                break;
            case 2:
                this.priority.setBackgroundColor(resources.getColor(R.color.alert_medium));
                break;
            case 3:
                this.priority.setBackgroundColor(resources.getColor(R.color.alert_low));
                break;
        }
    }
}
