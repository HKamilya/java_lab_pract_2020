package ru.itis;

import ru.itis.models.Student;
import ru.itis.repositories.StudentsRepository;
import ru.itis.repositories.StudentsRepositoryJdbcImpl;

import java.sql.*;

public class Main {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "qAzXcvbnm";


    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        StudentsRepository studentsRepository = new StudentsRepositoryJdbcImpl(connection);
        Student student = studentsRepository.findById(1L);
        System.out.println(student);
        student.setLastName("Сидиков");
        studentsRepository.update(student);
        System.out.println(studentsRepository.findById(1L));
        System.out.println(studentsRepository.findAllByAge(18));
        System.out.println(studentsRepository.findAll());
        Student student1 = new Student(null, "Хайруллин", "Ильдар", 21, 606);
        studentsRepository.save(student1);
        System.out.println(studentsRepository.findAllByAge(21));
        connection.close();
    }
}

