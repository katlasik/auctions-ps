package pl.sda.auctions.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Value("${mail.sender}")
    private String from;

    private final JavaMailSender mailSender;

    private final Logger logger = LoggerFactory.getLogger(MailService.class);

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    void send(String to, String subject, String text) {

        var message = new SimpleMailMessage();

        message.setTo(to);
        message.setText(text);
        message.setSubject(subject);
        message.setFrom(from);

        logger.info("Sending mail to {}.", to);

        mailSender.send(message);

    }


}
