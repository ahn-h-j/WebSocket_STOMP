package com.goorm.websocket_stomp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker//웹소켓 메시지 브로커(STOMP)가 동작
// STOMP(WebSocket 기반 메시징 프로토콜)를 설정하는 클래스
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    // 클라이언트가 WebSocket 서버에 연결할 수 있는 엔드포인트를 등록
    // 예: 클라이언트는 "/helloworld"로 WebSocket 연결을 시도
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // "/helloworld" 엔드포인트를 통해 WebSocket 연결을 지원하며,
        // SockJS를 사용해 WebSocket을 지원하지 않는 브라우저도 fallback 처리
        //서버쪽 엔드포인트와 클라이언트쪽 엔드포인트가 동일해야함
        registry.addEndpoint("/helloworld").withSockJS();
        //클라이언트랑 서버랑 웹소켓으로 연결해야할때 사용하는 url
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        // 클라이언트 A가 "/topic/room1"에 구독을 신청한다. (subscribe)
        // 클라이언트 B가 "/topic/room1"에 메시지를 전송한다. (send)
        // SimpleBroker가 중간에서 메시지를 수신하여 "/topic/room1"을 구독 중인 A에게 즉시 전달한다.
        // 서버는 별도의 로직 처리 없이 메시지를 중계하는 역할만 수행한다.
        config.enableSimpleBroker("/topic", "/queue");

        // 클라이언트가 "/app"으로 시작하는 경로로 메시지를 전송하면
        // 서버 Controller(@MessageMapping)로 전달되어 비즈니스 로직을 처리한 후 결과를 응답한다.
        // 예시: DB 저장, 데이터 검증, 데이터 가공 작업 수행 후 결과 반환
        config.setApplicationDestinationPrefixes("/app");
    }
}
