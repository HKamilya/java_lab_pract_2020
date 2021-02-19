package ru.itis.springbootdemo.util;


public interface MailsGenerator {
    String getMailForConfirm(String serverUrl, String code);
}
