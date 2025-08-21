package org.ops.engine.entity;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;

@SerdeImport
@Introspected
public class ContainerHealthStatus {
    private String containerName;
    private String status;
    private String ports;

    public ContainerHealthStatus(String containerName, String status, String ports) {
        this.containerName = containerName;
        this.status = status;
        this.ports = ports;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }
}
