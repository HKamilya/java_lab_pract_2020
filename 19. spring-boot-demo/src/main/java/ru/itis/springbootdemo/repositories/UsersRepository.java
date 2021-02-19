package ru.itis.springbootdemo.repositories;


import org.springframework.stereotype.Repository;
import ru.itis.springbootdemo.models.User;

import java.util.List;


public interface UsersRepository {

    void save(User user);

    List<User> findAll();
}
