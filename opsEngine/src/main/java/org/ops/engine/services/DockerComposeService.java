package org.ops.engine.services;

import jakarta.inject.Singleton;
import org.ops.engine.entity.DockerComposeDTO;
import org.ops.engine.entity.Status;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class DockerComposeService {
    private final Map<String, DockerComposeDTO> runningComposes = new ConcurrentHashMap<>();

    public DockerComposeDTO start(String composeFilePath) {
        String id = UUID.randomUUID().toString();
        String command = "docker compose -f " + composeFilePath + " up -d";

        if (executeCommand(command)) {
            DockerComposeDTO composeDTO = new DockerComposeDTO(id, composeFilePath, Status.RUNNING);
            runningComposes.put(id, composeDTO);
            return composeDTO;
        }
        throw new RuntimeException("Failed to start Docker Compose for file: " + composeFilePath);
    }

    public boolean stop(String id) {
        DockerComposeDTO composeDTO = runningComposes.get(id);
        if (composeDTO != null) {
            String command = "docker compose -f " + composeDTO.getPath() + " down";
            if (executeCommand(command)) {
                composeDTO.setStatus(Status.STOPPED);
                runningComposes.remove(id);
                return true;
            }
        }
        return false;
    }

    public List<DockerComposeDTO> list() {
        return new ArrayList<>(runningComposes.values());
    }

    public DockerComposeDTO get(String id) {
        return runningComposes.getOrDefault(id, new DockerComposeDTO(id, "N/A", Status.UNKNOWN));
    }

    private boolean executeCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            return process.exitValue() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
