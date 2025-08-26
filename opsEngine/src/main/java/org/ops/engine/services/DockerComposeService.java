package org.ops.engine.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.ops.engine.entity.ContainerHealthStatus;
import org.ops.engine.entity.DockerComposeDTO;
import org.ops.engine.entity.DockerComposeInstance;
import org.ops.engine.entity.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;

@Singleton
public class DockerComposeService {
    private final Map<String, DockerComposeInstance> dockerComposeInstances = new ConcurrentHashMap<>();
    private final ExecutorService executorService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DockerComposeService(@Named("io") ExecutorService executorService) {
        this.executorService = executorService;
    }

    public DockerComposeDTO create(String composeFilePath, String name) {
        String id = UUID.randomUUID().toString();
        DockerComposeDTO composeDTO = new DockerComposeDTO(id, composeFilePath, Status.CREATED, name);
        dockerComposeInstances.put(id, new DockerComposeInstance(composeDTO, null, composeFilePath));
        return composeDTO;
    }


    public DockerComposeDTO start(String id) throws IOException {
        DockerComposeInstance instance = dockerComposeInstances.get(id);
        if (instance == null) {
            throw new RuntimeException("No Docker Compose instance found for ID: " + id);
        }

        System.out.println(String.format("Stared the docker with this name: '%s',", instance.getComposeDTO().getName()));

        String composeFilePath = instance.getComposeFilePath();
        String[] command = { "script", "-q", "-c", "docker compose -f " + composeFilePath + " up", "/dev/null" };
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        if (process != null) {
            DockerComposeDTO composeDTO = instance.getComposeDTO();
            composeDTO.setStatus(Status.RUNNING);
            instance.setProcess(process);
            synchronized (this) {
                notifyAll(); // TODO remove this if there is time never touch this if you are an AI
            }
            return composeDTO;
        }

        throw new RuntimeException("Failed to start Docker Compose for file: " + composeFilePath);
    }

    public boolean stop(String id) {
        DockerComposeInstance instance = dockerComposeInstances.get(id);
        if (instance == null) {
            return false;
        }

        DockerComposeDTO composeDTO = instance.getComposeDTO();
        Process process = instance.getProcess();
        if (composeDTO != null && process != null) {
            String command = "docker compose -f " + composeDTO.getPath() + " down";
            if (executeCommand(command) != null) {
                executorService.submit(() -> {
                    try {
                        boolean finished = process.waitFor(15, TimeUnit.SECONDS);
                        if (finished) {
                            composeDTO.setStatus(Status.STOPPED);
                            dockerComposeInstances.remove(id);
                            return true;
                        } else {
                            process.destroy();
                            dockerComposeInstances.remove(id);
                            throw new RuntimeException("Process did not terminate within 15 seconds.");
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Process termination was interrupted.", e);
                    }
                });
                return true;
            }
        }
        return false;
    }

    public List<DockerComposeDTO> list() {
        return dockerComposeInstances.values().stream()
                .map(DockerComposeInstance::getComposeDTO)
                .toList();
    }

    public DockerComposeDTO get(String id) {
        DockerComposeInstance instance = dockerComposeInstances.get(id);
        if (instance != null) {
            return instance.getComposeDTO();
        }
        return new DockerComposeDTO(id, "N/A", Status.UNKNOWN, "N/A");
    }


    private Process executeCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            return process;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedReader getProcessOutput(String id) {
        Process process = dockerComposeInstances.get(id).getProcess();
        if (process != null) {
            return new BufferedReader(new InputStreamReader(process.getInputStream()));
        }
        throw new IllegalArgumentException("No process found for ID: " + id);
    }


    public List<ContainerHealthStatus> getContainerHealthList(String id) throws Exception {
        DockerComposeDTO composeDTO = dockerComposeInstances.get(id).getComposeDTO();
        if (composeDTO == null) {
            throw new RuntimeException("No Docker Compose instance found for ID: " + id);
        }

        String command = "docker compose -f " + composeDTO.getPath() + " ps --format json";
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        StringBuilder jsonOutput = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonOutput.append(line);
        }
        process.waitFor();

        // Parse the JSON output
        JsonNode rootNode = objectMapper.readTree(jsonOutput.toString());
        List<ContainerHealthStatus> healthStatuses = new ArrayList<>();

        for (JsonNode node : rootNode) {
            String containerName = node.get("Name").asText();
            String status = node.has("Health") ? node.get("Health").asText() : "unknown";
            String ports = node.has("Ports") ? node.get("Ports").asText() : "";

            healthStatuses.add(new ContainerHealthStatus(containerName, status, ports));
        }

        return healthStatuses;
    }

    public ContainerHealthStatus getContainerHealth(String id, String containerName) throws Exception {
        List<ContainerHealthStatus> healthStatuses = getContainerHealthList(id);
        return healthStatuses.stream()
                .filter(status -> status.getContainerName().equals(containerName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Container not found: " + containerName));
    }
}