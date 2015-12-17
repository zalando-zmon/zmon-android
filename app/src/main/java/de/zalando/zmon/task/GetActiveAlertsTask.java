package de.zalando.zmon.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;

import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.persistence.AlertDefinition;
import de.zalando.zmon.persistence.AlertDetails;

public class GetActiveAlertsTask extends HttpSafeAsyncTask<String, Void, List<AlertDetails>> {

    public GetActiveAlertsTask(Context context) {
        super(context);
    }

    @Override
    protected List<AlertDetails> callSafe(String... teams) {
        List<de.zalando.zmon.client.domain.AlertDetails> alertDetails;

        if (teams != null && teams.length != 0) {
            String teamQueryString = makeTeamString(teams);
            Log.d("[rest]", "list active alerts by teams: " + teamQueryString);
            alertDetails = ServiceFactory.createZmonService(context).getActiveAlerts(teamQueryString);
        } else {
            Log.d("[rest]", "list all active alerts");
            alertDetails = ServiceFactory.createZmonService(context).getActiveAlerts();
        }

        return map(alertDetails);
    }

    @NonNull
    private List<AlertDetails> map(List<de.zalando.zmon.client.domain.AlertDetails> activeAlerts) {
        return Lists.transform(activeAlerts, new Function<de.zalando.zmon.client.domain.AlertDetails, AlertDetails>() {
            @Override
            public AlertDetails apply(de.zalando.zmon.client.domain.AlertDetails input) {
                AlertDefinition alertDef = new AlertDefinition();
                alertDef.setAlertId(input.getAlertDefinition().getId());
                alertDef.setStatus(input.getAlertDefinition().getStatus());
                alertDef.setName(input.getAlertDefinition().getName());
                alertDef.setTeam(input.getAlertDefinition().getTeam());
                alertDef.setResponsibleTeam(input.getAlertDefinition().getResponsibleTeam());
                alertDef.setDescription(input.getAlertDefinition().getDescription());
                alertDef.setTags(input.getAlertDefinition().getTags());
                alertDef.setParentId(input.getAlertDefinition().getParentId());
                alertDef.setPeriod(input.getAlertDefinition().getPeriod());
                alertDef.setCheckDefinitionId(input.getAlertDefinition().getCheckDefinitionId());
                alertDef.setParameters(input.getAlertDefinition().getParameters());
                alertDef.setCondition(input.getAlertDefinition().getCondition());
                alertDef.setLastModified(input.getAlertDefinition().getLastModified());
                alertDef.setLastModifiedBy(input.getAlertDefinition().getLastModifiedBy());
                alertDef.setCloneable(input.getAlertDefinition().isCloneable());
                alertDef.setDeletable(input.getAlertDefinition().isDeletable());
                alertDef.setEditable(input.getAlertDefinition().isEditable());
                alertDef.setTemplate(input.getAlertDefinition().isTemplate());

                AlertDetails details = new AlertDetails();
                details.setMessage(input.getMessage());
                details.setAlertDefinition(alertDef);

                return details;
            }
        });
    }

    private String makeTeamString(String... teams) {
        return Joiner.on(",").join(teams);
    }
}
