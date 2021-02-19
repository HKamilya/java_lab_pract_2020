package ru.itis.springbootdemo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
public class EmailUtilImpl implements EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${profile}")
    private String profile;

    @Autowired
    private ExecutorService executorService;

    @Override
    public void sendMail(String to, String subject, String from, String text) {
        if (profile.equals("master")) {
            executorService.submit(() -> javaMailSender.send(mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setFrom(from);
                messageHelper.setTo(to);
                messageHelper.setSubject(subject);
                messageHelper.setText(text, true);
            }));
        } else if (profile.equals("dev")) {
            System.out.println(text);
        }
    }
}
