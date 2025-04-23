package com.softserve.academy.jdbc.example;

import java.util.Objects;

public class Student {
    private final long id;
    private final String name;
    private final String email;

    // Constructors but better do not use them
    public Student() {
        this.id = 0L;
        this.name = "";
        this.email = "";
    }

    public Student(long id, String name, String email) {
        if (name == null || email == null) {
            throw new IllegalArgumentException("Name and email cannot be null");
        }
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(name, student.name) && Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
