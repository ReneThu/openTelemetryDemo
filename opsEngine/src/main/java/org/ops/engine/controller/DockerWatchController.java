package org.ops.engine.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

import java.util.Map;

@Controller("/docker/watch")
public class DockerWatchController {

    @Get("/{id}")
    @View("dockerLogs")
    public Map<String, Object> watchLogs(String id) {
        return Map.of("id", id);
    }
}
