package com.softserve.academy.jdbc.example;

import java.util.Objects;

public class Enrollment {
    private final long id; // Змінено на long через BIGINT
    private final long studentId; // Змінено на long через BIGINT
    private final long courseId; // Змінено на long через BIGINT
    private final String grade; // Зроблено final для консистентності

    // Конструктор без параметрів
    public Enrollment() {
        this.id = 0L;
        this.studentId = 0L;
        this.courseId = 0L;
        this.grade = null;
    }

    // Основний конструктор із валідацією
    public Enrollment(long id, long studentId, long courseId, String grade) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be positive");
        }
        if (courseId <= 0) {
            throw new IllegalArgumentException("Course ID must be positive");
        }
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade; // Дозволяємо null для grade
    }

    public long getId() {
        return id;
    }

    public long getStudentId() {
        return studentId;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return id == that.id && studentId == that.studentId && courseId == that.courseId && Objects.equals(grade, that.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentId, courseId, grade);
    }

    @Override
    public String toString() {
        return "Enrollment{id=" + id + ", studentId=" + studentId + ", courseId=" + courseId + ", grade='" + grade + "'}";
    }
}