package org.ops.engine.controller;

import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import jakarta.inject.Inject;
import org.ops.engine.services.DockerComposeService;

import java.io.BufferedReader;
import java.io.IOException;

@ServerWebSocket("/docker/logs/{id}")
public class DockerLogWebSocket {

    @Inject
    private DockerComposeService dockerComposeService;

    private final WebSocketBroadcaster broadcaster;

    public DockerLogWebSocket(WebSocketBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @OnOpen
    public void onOpen(String id, WebSocketSession session) {
        new Thread(() -> {
            try {
                BufferedReader reader;
                synchronized (dockerComposeService) { //TODO this is shit remove that
                    while (true) {
                        try {
                            reader = dockerComposeService.getProcessOutput(id);
                            break; // Exit loop once process is available
                        } catch (IllegalArgumentException e) {
                            dockerComposeService.wait(); // Wait until notified
                        }
                    }
                }

                String line;


                while ((line = reader.readLine()) != null) {
                    if (line.contains("Enable Watc")) {
                        int i = 0;
                        i++;
                    }

                    line = line.replace("␛[0G␛7␛8␛[0G␛7␛[-9223372036854775808;0H␛[2K␛[1;38;2;0;0;0;48;2;255;255;255mw␛[0m␛[2m Enable Watch␛[0m␛[0G␛8␛[0G␛7␛8␛", "");
                    session.sendSync(line);
                }
            } catch (IOException | InterruptedException e) {
                session.sendSync("Error: " + e.getMessage());
                Thread.currentThread().interrupt(); // Restore interrupt status
            } finally {
                session.close();
            }
        }).start();
    }


    @OnMessage
    public void onMessage(String message, WebSocketSession session) {
    }
}
