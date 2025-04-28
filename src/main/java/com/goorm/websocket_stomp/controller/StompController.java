package com.goorm.websocket_stomp.controller;

import com.goorm.websocket_stomp.dto.ReqDto;
import com.goorm.websocket_stomp.dto.ResDto;
import com.goorm.websocket_stomp.dto.ResSessionsDto;
import com.goorm.websocket_stomp.listener.StompEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.EventListener;
import java.util.Set;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompController {

    private final StompEventListener stompEventListener;

    //여러 값을 받을 수 있음
    @MessageMapping("/hello")//수신 == /app/hello
    @SendTo("/topic/hello")//발신
    public ResDto basic(ReqDto reqDto){
        //Message<ReqDto> : 헤더를 포함한 전체를 볼 수 있음
        //MessageHeaders : 헤더 데이터를 볼 수 있음
        log.info("reqDto : {}", reqDto);

        return new ResDto(reqDto.getMessage().toUpperCase(), LocalDateTime.now());
    }

    @MessageMapping("/hello/{detail}")//수신 == /app/hello
    @SendTo("/topic/hello")//발신
    public ResDto basic(ReqDto reqDto, @DestinationVariable String detail){
        log.info("reqDto : {}", reqDto);

        return new ResDto(reqDto.getMessage().toUpperCase(), LocalDateTime.now());
    }

    @MessageMapping("/sessions")//수신 == /app/sessions
    @SendToUser("/queue/sessions")// == /user/queue/sessions
    //특정 세션에만 보내는 것
    // queue : 1:1 통신에 많이 사용하는 prefix
    public ResSessionsDto sessions(ReqDto reqDto, MessageHeaders headers){

        log.info("reqDto : {}", reqDto);
        String sessionId = headers.get("simpSessionId").toString();
        log.info("sessionId : {}", sessionId);

        Set<String> sessions = stompEventListener.getSessions();

        return new ResSessionsDto(sessions.size(), sessions.stream().toList(), sessionId, LocalDateTime.now());
    }
}
