package ru.itis.springbootdemo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User {
    private Long id;

    private String email;
    private String password;

    private State state;

    private String confirmCode;


    public enum State {
        CONFIRMED, NOT_CONFIRMED
    }
}
