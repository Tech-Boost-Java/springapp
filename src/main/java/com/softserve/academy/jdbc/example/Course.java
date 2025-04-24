package com.softserve.academy.jdbc.example;

import java.util.Objects;

public class Course {
    private final Long id;
    private final String title;
    private final long teacherId;


    public Course() {
        this.id = 0L;
        this.title = null;
        this.teacherId = 0L;
    }

    public Course(long id, String title, long teacherId) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        if (teacherId <= 0) {
            throw new IllegalArgumentException("Teacher ID must be positive");
        }
        this.id = id;
        this.title = title;
        this.teacherId = teacherId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getTeacherId() {
        return teacherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && teacherId == course.teacherId && Objects.equals(title, course.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, teacherId);
    }

    @Override
    public String toString() {
        return "Course{id=" + id + ", title='" + title + "', teacherId=" + teacherId + "}";
    }
}