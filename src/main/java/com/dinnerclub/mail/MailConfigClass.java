package com.dinnerclub.mail;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class MailConfigClass {
    @Value("${spring.mail.username}")
    private String sender;
}
