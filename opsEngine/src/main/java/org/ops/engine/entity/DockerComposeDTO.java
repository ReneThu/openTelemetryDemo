package org.ops.engine.entity;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;

import java.util.List;

@SerdeImport
@Introspected
public class DockerComposeDTO {
    private String id;
    private String path;
    private Status status;
    private String name;
    private List<ContainerHealthStatus> containerHealthStatuses;

    public DockerComposeDTO(String id, String path, Status status, String name) {
        this.id = id;
        this.path = path;
        this.status = status;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ContainerHealthStatus> getContainerHealthStatuses() {
        return containerHealthStatuses;
    }

    public void setContainerHealthStatuses(List<ContainerHealthStatus> containerHealthStatuses) {
        this.containerHealthStatuses = containerHealthStatuses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
