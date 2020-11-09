package ru.itis.javalab.homework;

import org.postgresql.ds.PGSimpleDataSource;


public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUser("postgres");
        dataSource.setPassword("qwerty1");
        dataSource.setURL("jdbc:postgresql://localhost:5432/postgres");

        EntityManager entityManager = new EntityManager(dataSource);
        User user = new User();
        user.setId(1L);
        user.setFirstName("Kamilya");
        user.setLastName("Khayrullina");
        user.setWorker(true);

        entityManager.createTable("user", user.getClass());
        entityManager.save("user", user);
        User user1 = entityManager.findById("user", User.class, Long.class, 1L);
        System.out.println(user1);
    }
}
