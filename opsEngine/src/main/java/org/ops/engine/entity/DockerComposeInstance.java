package org.ops.engine.entity;

public class DockerComposeInstance {
    private DockerComposeDTO composeDTO;
    private Process process;
    private String composeFilePath;

    public DockerComposeInstance(DockerComposeDTO composeDTO, Process process, String composeFilePath) {
        this.composeDTO = composeDTO;
        this.process = process;
        this.composeFilePath = composeFilePath;
    }

    public DockerComposeDTO getComposeDTO() {
        return composeDTO;
    }

    public void setComposeDTO(DockerComposeDTO composeDTO) {
        this.composeDTO = composeDTO;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public String getComposeFilePath() {
        return composeFilePath;
    }

    public void setComposeFilePath(String composeFilePath) {
        this.composeFilePath = composeFilePath;
    }
}

