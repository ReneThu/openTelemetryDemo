package org.ops.engine.entity;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;

@SerdeImport
@Introspected
public class DockerComposeDTO {
    private String id;
    private String path;
    private Status status;

    public DockerComposeDTO(String id, String path, Status status) {
        this.id = id;
        this.path = path;
        this.status = status;
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
}
