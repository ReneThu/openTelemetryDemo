package org.ops.engine.services;

import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.ops.engine.entity.DockerComposeDTO;
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
    private final Map<String, DockerComposeDTO> runningComposes = new ConcurrentHashMap<>(); //TODO those three maps are shit remove them
    private final Map<String, Process> processMap = new ConcurrentHashMap<>();
    private final Map<String, String> composeFilePaths = new ConcurrentHashMap<>();
    private final ExecutorService executorService;

    public DockerComposeService(@Named("io") ExecutorService executorService) {
        this.executorService = executorService;
    }

    public DockerComposeDTO create(String composeFilePath) {
        String id = UUID.randomUUID().toString();
        composeFilePaths.put(id, composeFilePath);
        return new DockerComposeDTO(id, composeFilePath, Status.CREATED);
    }

    public DockerComposeDTO start(String id) throws IOException {
        String composeFilePath = composeFilePaths.get(id);
        if (composeFilePath == null) {
            throw new RuntimeException("No Docker Compose file path found for ID: " + id);
        }

        String[] command = { "script", "-q", "-c", "docker compose -f " + composeFilePath + " up", "/dev/null" };
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();


        if (process != null) {
            DockerComposeDTO composeDTO = new DockerComposeDTO(id, composeFilePath, Status.RUNNING);
            runningComposes.put(id, composeDTO);
            synchronized (this) {
                processMap.put(id, process);
                notifyAll(); // TODO remove this if there is time
            }
            return composeDTO;
        }
        throw new RuntimeException("Failed to start Docker Compose for file: " + composeFilePath);
    }


    public boolean stop(String id) {
        DockerComposeDTO composeDTO = runningComposes.get(id);
        Process process = processMap.get(id);
        if (composeDTO != null && process != null) {
            String command = "docker compose -f " + composeDTO.getPath() + " down";
            if (executeCommand(command) != null) {
                executorService.submit(() -> {
                    try {
                        boolean finished = process.waitFor(15, TimeUnit.SECONDS);
                        if (finished) {
                            composeDTO.setStatus(Status.STOPPED);
                            runningComposes.remove(id);
                            processMap.remove(id);
                            return true;
                        } else {
                            process.destroy();
                            processMap.remove(id);
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
        return new ArrayList<>(runningComposes.values());
    }

    public DockerComposeDTO get(String id) {
        return runningComposes.getOrDefault(id, new DockerComposeDTO(id, "N/A", Status.UNKNOWN));
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
        Process process = processMap.get(id);
        if (process != null) {
            return new BufferedReader(new InputStreamReader(process.getInputStream()));
        }
        throw new IllegalArgumentException("No process found for ID: " + id);
    }
}