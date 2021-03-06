package ru.itis.javalab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ru.itis.javalab.repositories.UsersRepository;
import ru.itis.javalab.models.User;

import java.util.UUID;

@Component
public class SignUpServiceImpl implements SignUpService {

    private EmailsService emailsService;
    private UsersRepository usersRepository;

    public SignUpServiceImpl(EmailsService emailsService, UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.emailsService = emailsService;
    }


    @Override
    public void signUp(User user) {
        String confirmCode = UUID.randomUUID().toString();

        user.setConfirmCode(confirmCode);

        String url = "https://itdrive.pro/confirm/" + confirmCode;

        usersRepository.save(user);
        emailsService.sendMail("Подтверждение регистрации", url, user.getEmail());
    }
}
