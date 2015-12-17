package de.zalando.zmon.task;

import android.content.Context;
import android.util.Log;

import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.persistence.AlertDefinition;
import de.zalando.zmon.persistence.AlertDetails;

public class GetAlertDetailsTask extends HttpSafeAsyncTask<String, Void, AlertDetails> {

    public GetAlertDetailsTask(Context context) {
        super(context);
    }

    @Override
    protected AlertDetails callSafe(String... params) {
        Log.d("[rest]", "get alert details of alert: " + params[0]);

        de.zalando.zmon.client.domain.AlertDetails input =
                ServiceFactory.createZmonService(context).getAlertDetails(params[0]);

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
}
