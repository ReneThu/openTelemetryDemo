package com.example.otelDemo.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final RabbitTemplate rabbitTemplate;

    public MessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(Long guestbookEntryId) {
        rabbitTemplate.convertAndSend("guestBookNotifications", guestbookEntryId);
    }
}
