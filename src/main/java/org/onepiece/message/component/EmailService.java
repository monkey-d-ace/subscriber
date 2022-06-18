package org.onepiece.message.component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailService {
    public void onReceiveMessage(String message) {
        log.info("email service receive this message: {}, then will send an email to user", message);
    }
}
