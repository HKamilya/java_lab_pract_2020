package ru.itis.javalab;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepositoryJdbcImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("qwerty");
        hikariConfig.setMaximumPoolSize(20);

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        UsersRepositoryJdbcImpl usersRepositoryJdbc = new UsersRepositoryJdbcImpl(dataSource);
        List<User> users = usersRepositoryJdbc.findAllByAge(20);
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i));
        }
        List<User> users2 = usersRepositoryJdbc.findAll();
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users2.get(i));
        }
        System.out.println(usersRepositoryJdbc.findById(1L));
    }
}
