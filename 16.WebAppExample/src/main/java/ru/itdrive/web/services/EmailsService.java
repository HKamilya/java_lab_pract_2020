package ru.itdrive.web.services;

public interface EmailsService {
    void sendMail(String subject, String text, String email);
}
