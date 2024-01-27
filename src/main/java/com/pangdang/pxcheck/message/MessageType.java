package com.pangdang.pxcheck.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageType {

    NORMAL("message.normal"),
    ERROR("message.error"),
    MAIN("message.main");

    private final String type;
}