package com.example.otelDemo.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @RabbitListener(queues = "guestBookNotifications")
    public void receiveMessage(Long guestbookEntryId) {
        System.out.println("Received message with Guestbook Entry ID: " + guestbookEntryId);
    }
}
