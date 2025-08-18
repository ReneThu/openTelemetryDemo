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

    // Triggered when a WebSocket connection is opened
    @OnOpen
    public void onOpen(String id, WebSocketSession session) {
        try {
            BufferedReader reader = dockerComposeService.getProcessOutput(id);
            new Thread(() -> {
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        session.sendSync(line); // Send each log line to the client
                    }
                } catch (IOException e) {
                    session.sendSync("Error reading logs: " + e.getMessage());
                } finally {
                    session.close(); // Close session when done
                }
            }).start();
        } catch (IllegalArgumentException e) {
            session.sendSync("Error: " + e.getMessage());
            session.close();
        }
    }

    // Required by Micronaut to handle incoming messages (even if unused)
    @OnMessage
    public void onMessage(String message, WebSocketSession session) {
        // No-op: This method is required by Micronaut but can remain empty if not used
    }
}
