package com.softserve.academy.jdbc.dao;

import com.softserve.academy.jdbc.example.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for Teacher entity.
 * This class is responsible for data access operations related to the Teacher entity.
 */
public class TeacherDAO {
    private final Connection conn;

    public TeacherDAO(Connection conn) {
        if (conn == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        this.conn = conn;
    }

    /**
     * Adds a new teacher to the database.
     *
     * @param teacher The teacher object to be added.
     * @return The generated ID of the new teacher.
     * @throws SQLException If a database access error occurs.
     */
    public long addTeacher(Teacher teacher) throws SQLException {
        if (teacher == null || teacher.getName() == null || teacher.getDepartment() == null) {
            throw new IllegalArgumentException("Teacher and its fields cannot be null");
        }
        String sql = "INSERT INTO teacher (name, department) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, teacher.getName());
            pstmt.setString(2, teacher.getDepartment());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            throw new SQLException("Failed to retrieve generated ID for the new teacher");
        }
    }

    /**
     * Updates an existing teacher in the database.
     *
     * @param teacher The teacher object with updated data.
     * @return True if the teacher was updated, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean updateTeacher(Teacher teacher) throws SQLException {
        if (teacher == null || teacher.getName() == null || teacher.getDepartment() == null) {
            throw new IllegalArgumentException("Teacher and its fields cannot be null");
        }
        String sql = "UPDATE teacher SET name = ?, department = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, teacher.getName());
            pstmt.setString(2, teacher.getDepartment());
            pstmt.setLong(3, teacher.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Deletes a teacher from the database.
     *
     * @param id The ID of the teacher to be deleted.
     * @return True if the teacher was deleted, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean deleteTeacher(long id) throws SQLException {
        String sql = "DELETE FROM teacher WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Retrieves a teacher by ID from the database.
     *
     * @param id The ID of the teacher to be retrieved.
     * @return The teacher object if found, null otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public Teacher getTeacherById(long id) throws SQLException {
        String sql = "SELECT * FROM teacher WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Teacher(rs.getLong("id"), rs.getString("name"), rs.getString("department"));
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all teachers from the database.
     *
     * @return A list of all teacher objects.
     * @throws SQLException If a database access error occurs.
     */
    public List<Teacher> getAllTeachers() throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teacher";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Teacher teacher = new Teacher(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("department")
                );
                teachers.add(teacher);
            }
        }
        return teachers;
    }

    /**
     * Retrieves teachers by name from the database.
     *
     * @param name The name of the teacher to be retrieved.
     * @return A list of teacher objects with the given name.
     * @throws SQLException If a database access error occurs.
     */
    public List<Teacher> getTeachersByName(String name) throws SQLException {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teacher WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Teacher teacher = new Teacher(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("department")
                    );
                    teachers.add(teacher);
                }
            }
        }
        return teachers;
    }
}