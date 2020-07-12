package ru.itis.models;


import java.util.Objects;

public class Mentor {
    private Long id;
    private String firstName;
    private String lastName;
    private Student student;
    private int subject_id;

    public Mentor(Long id, String firstName, String lastName, Student student) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.student = student;
    }

    public Mentor(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Mentor(Long id, String firstName, String lastName, Student student, int subject_id) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.student = student;
        this.subject_id = subject_id;
    }

    public Long getId() {
        return id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mentor mentor = (Mentor) o;
        return subject_id == mentor.subject_id &&
                Objects.equals(id, mentor.id) &&
                Objects.equals(firstName, mentor.firstName) &&
                Objects.equals(lastName, mentor.lastName) &&
                Objects.equals(student, mentor.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, student, subject_id);
    }

    @Override
    public String toString() {
        return "Mentor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", student=" + student +
                ", subject_id=" + subject_id +
                '}';
    }
}

