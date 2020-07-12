package ru.itis.repositories;

import ru.itis.models.Student;

import java.util.List;


public interface StudentsRepository extends CrudRepository<Student> {
    List<Student> findAllByAge(int age);
}

