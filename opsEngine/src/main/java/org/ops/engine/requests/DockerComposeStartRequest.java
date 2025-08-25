package org.ops.engine.requests;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;

@SerdeImport
@Introspected
public class DockerComposeStartRequest {
    private String path;
    private String name;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
