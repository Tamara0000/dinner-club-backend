package com.dinnerclub.mail;

public interface MailService {
    void sendMailToGuest(String to, String subject, String text);
}
