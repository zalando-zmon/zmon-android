package de.zalando.zmon.client.domain;

import java.util.List;
import java.util.Set;

public class ZmonStatus {

    private int workersActive;
    private int workersTotal;

    private int queueSize;

    private Set<ZmonQueue> queues;
    private Set<ZmonWorker> workers;

    public int getWorkersActive() {
        return workersActive;
    }

    public void setWorkersActive(int workersActive) {
        this.workersActive = workersActive;
    }

    public int getWorkersTotal() {
        return workersTotal;
    }

    public void setWorkersTotal(int workersTotal) {
        this.workersTotal = workersTotal;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public Set<ZmonQueue> getQueues() {
        return queues;
    }

    public void setQueues(Set<ZmonQueue> queues) {
        this.queues = queues;
    }

    public Set<ZmonWorker> getWorkers() {
        return workers;
    }

    public void setWorkers(Set<ZmonWorker> workers) {
        this.workers = workers;
    }
}
