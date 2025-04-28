package com.goorm.websocket_stomp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResDto {
    private String message;
    private LocalDateTime localDateTime;
}
