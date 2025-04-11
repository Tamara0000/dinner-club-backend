package com.dinnerclub.mail.impl;

import com.dinnerclub.mail.MailConfigClass;
import com.dinnerclub.mail.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final MailConfigClass mailConfigClass;

    public void sendMailToGuest(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(mailConfigClass.getSender());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text
                    + "<br><br>"
                    + "Sincerely, <br>"
                    + "Your host", true);

            mailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
