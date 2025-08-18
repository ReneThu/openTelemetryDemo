package org.ops.engine.controller;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.serde.annotation.SerdeImport;
import org.ops.engine.entity.DockerComposeDTO;
import org.ops.engine.requests.DockerComposeStartRequest;
import org.ops.engine.services.DockerComposeService;

import java.util.List;

@SerdeImport(DockerComposeStartRequest.class)
@Controller("/docker-compose")
public class DockerComposeController {

    private final DockerComposeService dockerComposeService;

    public DockerComposeController(DockerComposeService dockerComposeService) {
        this.dockerComposeService = dockerComposeService;
    }

    @Post("/start")
    public DockerComposeDTO start(@Body DockerComposeStartRequest request) {
        return dockerComposeService.start(request.getPath());
    }

    @Post("/stop/{id}")
    public HttpStatus stop(@PathVariable String id) {
        boolean stopped = dockerComposeService.stop(id);
        return stopped ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }

    @Get("/list")
    public List<DockerComposeDTO> list() {
        return dockerComposeService.list();
    }

    @Get("/get/{id}")
    public DockerComposeDTO get(@PathVariable String id) {
        DockerComposeDTO composeDTO = dockerComposeService.get(id);
        if (composeDTO == null) {
            throw new RuntimeException("Docker Compose instance not found for ID: " + id);
        }
        return composeDTO;
    }
}
