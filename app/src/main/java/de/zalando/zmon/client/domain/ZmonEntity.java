package de.zalando.zmon.client.domain;

import com.google.common.base.MoreObjects;

public class ZmonEntity {

    private String entity;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("entity", entity).toString();
    }
}
