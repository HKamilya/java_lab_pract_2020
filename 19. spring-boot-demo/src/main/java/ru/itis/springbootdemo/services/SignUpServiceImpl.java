package ru.itis.springbootdemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.itis.springbootdemo.dto.UserForm;
import ru.itis.springbootdemo.models.User;
import ru.itis.springbootdemo.repositories.UsersRepository;
import ru.itis.springbootdemo.util.EmailUtil;
import ru.itis.springbootdemo.util.MailsGenerator;

import java.util.UUID;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UsersRepository usersRepository;


    @Autowired
    private MailsGenerator mailsGenerator;

    @Autowired
    private EmailUtil emailUtil;

    @Value("${server.url}")
    private String serverUrl;

    @Value("${mail.username}")
    private String from;

    @Override
    public void signUp(UserForm form) {
        User newUser = User.builder()
                .email(form.getEmail())
                .password(form.getPassword())
                .confirmCode(UUID.randomUUID().toString())
                .state(User.State.NOT_CONFIRMED)
                .build();

        usersRepository.save(newUser);
        String confirmMail = mailsGenerator.getMailForConfirm(serverUrl, newUser.getConfirmCode());
        emailUtil.sendMail(newUser.getEmail(), "Регистрация", from, confirmMail);
    }
}
