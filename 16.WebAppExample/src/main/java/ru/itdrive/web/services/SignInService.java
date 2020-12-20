package ru.itdrive.web.services;

public interface SignInService {
    boolean authenticate(String email, String password);
}
