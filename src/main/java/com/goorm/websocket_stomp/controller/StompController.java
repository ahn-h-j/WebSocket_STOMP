package com.goorm.websocket_stomp.controller;

import com.goorm.websocket_stomp.dto.ReqDto;
import com.goorm.websocket_stomp.dto.ResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompController {
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
}
