package de.zalando.zmon.util;

import de.zalando.zmon.persistence.AlertDefinition;
import de.zalando.zmon.persistence.AlertDetails;
import de.zalando.zmon.persistence.AlertParameters;
import de.zalando.zmon.persistence.NotificationThreshold;

public class EntityTransformator {

    public static AlertDetails transform(de.zalando.zmon.client.domain.AlertDetails input) {
        NotificationThreshold notificationThreshold = null;

        if (input.getAlertDefinition().getParameters() != null && input.getAlertDefinition().getParameters().getNotificationThreshold() != null) {
            notificationThreshold = new NotificationThreshold(
                    input.getAlertDefinition().getParameters().getNotificationThreshold().getComment(),
                    input.getAlertDefinition().getParameters().getNotificationThreshold().getValue(),
                    input.getAlertDefinition().getParameters().getNotificationThreshold().getType()
            );
        }

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
        alertDef.setParameters(new AlertParameters(notificationThreshold));
        alertDef.setCondition(input.getAlertDefinition().getCondition());
        alertDef.setLastModified(input.getAlertDefinition().getLastModified());
        alertDef.setLastModifiedBy(input.getAlertDefinition().getLastModifiedBy());
        alertDef.setCloneable(input.getAlertDefinition().isCloneable());
        alertDef.setDeletable(input.getAlertDefinition().isDeletable());
        alertDef.setEditable(input.getAlertDefinition().isEditable());
        alertDef.setTemplate(input.getAlertDefinition().isTemplate());
        alertDef.setPriority(input.getAlertDefinition().getPriority());

        AlertDetails details = new AlertDetails();
        details.setMessage(input.getMessage());
        details.setAlertDefinition(alertDef);

        return details;
    }
}
