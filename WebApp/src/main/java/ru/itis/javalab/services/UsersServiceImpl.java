package ru.itis.javalab.services;

import ru.itis.javalab.dto.UserDto;
import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Optional;

import static ru.itis.javalab.dto.UserDto.from;

public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public List<UserDto> getAllUser(int page, int size) {
        return from(usersRepository.findAll(page, size));
    }

    @Override
    public void addUser(UserDto userDto) {
        usersRepository.save(User.builder()
                .age(null)
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build());
    }

    @Override
    public User findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public void insertUUID(String username, String uuid) {
        usersRepository.updateByUsername(username, uuid);
    }

    public Optional<User> findByUuid(String uuid) {
        return usersRepository.findByUuid(uuid);
    }
}
