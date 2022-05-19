package com.example.simplechat.listener;

import com.example.simplechat.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection, event: {}", event);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        Optional.ofNullable(StompHeaderAccessor.wrap(event.getMessage()).getSessionAttributes())
                .flatMap(attributes -> Optional.ofNullable(attributes.get("username")))
                .ifPresent(username -> {
                    log.info("User Disconnected : {}", username);
                    messagingTemplate.convertAndSend("/topic/publicChatRoom",
                            ChatMessage.builder()
                                    .type(ChatMessage.MessageType.LEAVE)
                                    .sender(String.valueOf(username))
                                    .build());
                });
    }
}