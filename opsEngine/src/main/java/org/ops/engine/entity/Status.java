package org.ops.engine.entity;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;

@SerdeImport
@Introspected
public enum Status {
    RUNNING,
    STOPPED,
    UNKNOWN
}
