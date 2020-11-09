package ru.itis.services;

public interface UsersService {
    void signUp(String firstName, String lastName, String email, String password);
    void signIn(String email, String password);
}
