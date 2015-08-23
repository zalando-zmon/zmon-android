package de.zalando.zmon.client.domain;

import java.util.Date;

public class ZmonWorker {

    private Date lastExecutionTime;
    private String name;

    public ZmonWorker(Date lastExecutionTime, String name) {
        this.lastExecutionTime = lastExecutionTime;
        this.name = name;
    }

    public Date getLastExecutionTime() {
        return lastExecutionTime;
    }

    public void setLastExecutionTime(Date lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
