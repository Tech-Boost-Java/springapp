package com.softserve.academy.jdbc.dao;

import com.softserve.academy.jdbc.example.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentDAO class is responsible for data access operations related to Student entities.
 * It provides methods to interact with the database and perform CRUD operations.
 */
public class StudentDAO {
    private final Connection conn;

    public StudentDAO(Connection conn) {
        if (conn == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        this.conn = conn;
    }

    /**
     * Adds a new student to the database.
     *
     * @param student The student object to be added.
     * @return The generated ID of the new student.
     * @throws SQLException If a database access error occurs.
     */
    public long addStudent(Student student) throws SQLException {
        if (student == null || student.getName() == null || student.getEmail() == null) {
            throw new IllegalArgumentException("Student and its fields cannot be null");
        }
        String sql = "INSERT INTO student (name, email) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.executeUpdate();

            // Отримання згенерованого ID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            throw new SQLException("Failed to retrieve generated ID for the new student");
        }
    }

    /**
     * Updates an existing student in the database.
     *
     * @param student The student object with updated data.
     * @return True if the student was updated, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean updateStudent(Student student) throws SQLException {
        if (student == null || student.getName() == null || student.getEmail() == null) {
            throw new IllegalArgumentException("Student and its fields cannot be null");
        }
        String sql = "UPDATE student SET name = ?, email = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setLong(3, student.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Deletes a student from the database.
     *
     * @param id The ID of the student to be deleted.
     * @return True if the student was deleted, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean deleteStudent(long id) throws SQLException {
        String sql = "DELETE FROM student WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Retrieves a student by ID from the database.
     *
     * @param id The ID of the student to be retrieved.
     * @return The student object if found, null otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public Student getStudentById(long id) throws SQLException {
        String sql = "SELECT * FROM student WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(rs.getLong("id"), rs.getString("name"), rs.getString("email"));
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all students from the database.
     *
     * @return A list of all student objects.
     * @throws SQLException If a database access error occurs.
     */
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                students.add(new Student(rs.getLong("id"), rs.getString("name"), rs.getString("email")));
            }
        }
        return students;
    }
}