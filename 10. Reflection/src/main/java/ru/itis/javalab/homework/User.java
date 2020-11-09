package ru.itis.javalab.homework;

import lombok.Data;


@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private boolean isWorker;

    User() {

    }

}
