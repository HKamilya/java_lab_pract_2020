package ru.itis.repositories;

import ru.itis.models.Mentor;
import ru.itis.models.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class StudentsRepositoryJdbcImpl implements StudentsRepository {

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select s.id as s_id, s.first_name as s_first_name, s.last_name as s_last_name, m.id as m_id, m.first_name as m_first_name, m.last_name as m_last_name,  *\n" +
            "            from student s\n" +
            "             left join mentor m on s.id = m.student_id where s.id =";
    //language=SQL
    private static final String SQL_SELECT_BY_AGE = "select s.id as s_id, s.first_name as s_first_name, s.last_name as s_last_name, m.id as m_id, m.first_name as m_first_name, m.last_name as m_last_name,  *\n" +
            "            from student s\n" +
            "             left join mentor m on s.id = m.student_id where age =";
    //language=SQL
    private static final String SQL_INSERT = "insert into student (first_name, last_name, age, group_number) values ( ";
    //language=SQL
    private static final String SQL_UPDATE = "update student set ";
    //language=SQL
    private static final String SQL_SELECT = "select s.id as s_id, s.first_name as s_first_name, s.last_name as s_last_name, m.id as m_id, m.first_name as m_first_name, m.last_name as m_last_name,  *\n" +
            "            from student s\n" +
            "             left join mentor m on s.id = m.student_id";


    private Connection connection;

    public StudentsRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Student> findAllByAge(int age) {
        Statement statement = null;
        ResultSet result = null;
        List<Student> students = new ArrayList<>();
        HashMap<Student, List<Mentor>> tempHM = new LinkedHashMap<>();
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(SQL_SELECT_BY_AGE + age);
            while (result.next()) {
                Mentor mentor = new Mentor(result.getLong("m_id"),
                        result.getString("m_first_name"),
                        result.getString("m_last_name"));
                Student student = new Student(result.getLong("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getInt("age"),
                        result.getInt("group_number"));
                if (tempHM.containsKey(student)) {
                    List<Mentor> temp = tempHM.get(student);
                    if (!temp.contains(mentor)) {
                        temp.add(mentor);
                    }
                    tempHM.put(student, temp);
                } else {
                    List<Mentor> temp = new ArrayList<>();
                    temp.add(mentor);
                    tempHM.put(student, temp);
                }
            }
            for (Map.Entry<Student, List<Mentor>> entry : tempHM.entrySet()) {
                Student student = entry.getKey();
                List<Mentor> mentors = entry.getValue();
                student.setMentors(mentors);
                students.add(student);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
        return students;
    }


    // Необходимо вытащить список всех студентов, при этом у каждого студента должен быть проставлен список менторов
    // у менторов в свою очередь ничего проставлять (кроме имени, фамилии, id не надо)
    // student1(id, firstName, ..., mentors = [{id, firstName, lastName, null}, {}, ), student2, student3
    // все сделать одним запросом
    @Override
    public List<Student> findAll() {
        Statement statement = null;
        ResultSet result = null;
        List<Student> students = new ArrayList<>();
        HashMap<Student, List<Mentor>> tempHM = new LinkedHashMap<>();
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(SQL_SELECT);
            while (result.next()) {
                Mentor mentor = new Mentor(result.getLong("m_id"),
                        result.getString("m_first_name"),
                        result.getString("m_last_name"));
                Student student = new Student(result.getLong("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getInt("age"),
                        result.getInt("group_number"));
                if (tempHM.containsKey(student)) {
                    List<Mentor> temp = tempHM.get(student);
                    if (!temp.contains(mentor)) {
                        temp.add(mentor);
                    }
                    tempHM.put(student, temp);
                } else {
                    List<Mentor> temp = new ArrayList<>();
                    temp.add(mentor);
                    tempHM.put(student, temp);
                }
            }
            for (Map.Entry<Student, List<Mentor>> entry : tempHM.entrySet()) {
                Student student = entry.getKey();
                List<Mentor> mentors = entry.getValue();
                student.setMentors(mentors);
                students.add(student);
            }
        } catch (
                SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
        return students;
    }

    @Override
    public Student findById(Long id) {
        Statement statement = null;
        ResultSet result = null;
        Student student = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(SQL_SELECT_BY_ID + id);
            List<Mentor> mentors = new ArrayList<>();
            while (result.next()) {
                Mentor mentor = new Mentor(result.getLong("m_id"),
                        result.getString("m_first_name"),
                        result.getString("m_last_name"));
                student = new Student(result.getLong("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getInt("age"),
                        result.getInt("group_number"));
                mentors.add(mentor);
                student.setMentors(mentors);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
        return student;
    }


    // просто вызывается insert для сущности
    // student = Student(null, 'Марсель', 'Сидиков', 26, 915)
    // studentsRepository.save(student);
    // // student = Student(3, 'Марсель', 'Сидиков', 26, 915)
    @Override
    public void save(Student entity) {
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            String string = (SQL_INSERT +
                    "'" + entity.getFirstName() + "', " +
                    "'" + entity.getLastName() + "', " + entity.getAge() + ", " + entity.getGroupNumber() + ")" + "returning id" +
                    ";");
            result = statement.executeQuery(string);
            if (result.next()) {
                entity.setId((long) result.getRow());
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }


    // для сущности, у которой задан id выполнить обновление всех полей
    // ->
    // student = Student(3, 'Марсель', 'Сидиков', 26, 915)
    // student.setFirstName("Игорь")
    // student.setLastName(null);
    // studentsRepository.update(student);
    // (3, 'Игорь', null, 26, 915)

    @Override
    public void update(Student entity) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String string = (SQL_UPDATE +
                    " first_name = '" + entity.getFirstName() + "', " +
                    "last_name ='" + entity.getLastName() + "', age = " + entity.getAge() + ", group_number = " + entity.getGroupNumber() +
                    " where id = " + entity.getId() + ";");
            statement.executeUpdate(string);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }
}


