package ru.itis.javalab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;

import java.util.Optional;

public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;


    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return usersRepository.findOne(id);
    }

    @Override
    public void deleteUserById(long userId) {
        usersRepository.findOne(userId)
                .ifPresent(
                        user -> {
                            user.setIsDeleted(true);
                            usersRepository.update(user);
                        }
                );
    }
}
