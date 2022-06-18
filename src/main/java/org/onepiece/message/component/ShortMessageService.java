package org.onepiece.message.component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShortMessageService {
    public void whenReceiveThen(String message) {
        log.info("short message service receive the message: {}, then will send a short message to user's phone", message);
    }
}
