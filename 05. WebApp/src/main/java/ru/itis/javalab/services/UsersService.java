package ru.itis.javalab.services;

import ru.itis.javalab.dto.UserDto;
import ru.itis.javalab.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {


    List<User> getAllUsers();

    List<UserDto> getAllUser(int page, int size);

    void addUser(UserDto userDto);

    User findByUsername(String username);

    Optional<User> findByUuid(String uuid);


    void updateUser(User user);
}
