package com.softserve.academy.jdbc.dao;

import com.softserve.academy.jdbc.example.Enrollment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for managing enrollments in the database.
 * This class is responsible for data access operations related to the Enrollment entity.
 * It provides methods to interact with the database and perform CRUD operations.
 */
public class EnrollmentDAO {
    private final Connection conn;

    public EnrollmentDAO(Connection conn) {
        if (conn == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        this.conn = conn;
    }

    /**
     * Adds a new enrollment to the database.
     *
     * @param enrollment The enrollment object to be added.
     * @return The generated ID of the new enrollment.
     * @throws SQLException If a database access error occurs.
     */
    public long addEnrollment(Enrollment enrollment) throws SQLException {
        if (enrollment == null) {
            throw new IllegalArgumentException("Enrollment cannot be null");
        }
        if (enrollment.getStudentId() <= 0) {
            throw new IllegalArgumentException("Student ID must be positive");
        }
        if (enrollment.getCourseId() <= 0) {
            throw new IllegalArgumentException("Course ID must be positive");
        }
        String sql = "INSERT INTO enrollment (student_id, course_id, grade) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, enrollment.getStudentId());
            pstmt.setLong(2, enrollment.getCourseId());
            pstmt.setString(3, enrollment.getGrade());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            throw new SQLException("Failed to retrieve generated ID for the new enrollment");
        }
    }

    /**
     * Updates an existing enrollment in the database.
     *
     * @param enrollment The enrollment object with updated data.
     * @return True if the enrollment was updated, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean updateEnrollment(Enrollment enrollment) throws SQLException {
        if (enrollment == null) {
            throw new IllegalArgumentException("Enrollment cannot be null");
        }
        if (enrollment.getStudentId() <= 0) {
            throw new IllegalArgumentException("Student ID must be positive");
        }
        if (enrollment.getCourseId() <= 0) {
            throw new IllegalArgumentException("Course ID must be positive");
        }
        String sql = "UPDATE enrollment SET student_id = ?, course_id = ?, grade = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, enrollment.getStudentId());
            pstmt.setLong(2, enrollment.getCourseId());
            pstmt.setString(3, enrollment.getGrade());
            pstmt.setLong(4, enrollment.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Deletes an enrollment from the database.
     *
     * @param id The ID of the enrollment to be deleted.
     * @return True if the enrollment was deleted, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean deleteEnrollment(long id) throws SQLException {
        String sql = "DELETE FROM enrollment WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Retrieves an enrollment by ID from the database.
     *
     * @param id The ID of the enrollment to be retrieved.
     * @return The enrollment object if found, null otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public Enrollment getEnrollmentById(long id) throws SQLException {
        String sql = "SELECT * FROM enrollment WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Enrollment(
                            rs.getLong("id"),
                            rs.getLong("student_id"),
                            rs.getLong("course_id"),
                            rs.getString("grade")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all enrollments from the database.
     *
     * @return A list of all enrollment objects.
     * @throws SQLException If a database access error occurs.
     */
    public List<Enrollment> getAllEnrollments() throws SQLException {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollment";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Enrollment enrollment = new Enrollment(
                        rs.getLong("id"),
                        rs.getLong("student_id"),
                        rs.getLong("course_id"),
                        rs.getString("grade")
                );
                enrollments.add(enrollment);
            }
        }
        return enrollments;
    }
}