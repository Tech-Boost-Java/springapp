package com.softserve.academy.jdbc.example;

import java.util.Objects;

public class Teacher {
    private final long id;
    private final String name;
    private final String department;

  /// Constructors but better do not use them
    public Teacher() {
        this.id = 0L;
        this.name = null;
        this.department = null;
    }

    public Teacher(long id, String name, String department) {
        if (name == null || department == null) {
            throw new IllegalArgumentException("Name and department cannot be null");
        }
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id && Objects.equals(name, teacher.name) && Objects.equals(department, teacher.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, department);
    }

    @Override
    public String toString() {
        return "Teacher{id=" + id + ", name='" + name + "', department='" + department + "'}";
    }
}