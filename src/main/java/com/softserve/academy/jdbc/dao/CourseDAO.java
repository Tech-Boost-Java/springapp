package com.softserve.academy.jdbc.dao;

import com.softserve.academy.jdbc.example.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * CourseDAO class is responsible for data access operations related to Course entities.
 * It provides methods to interact with the database and perform CRUD operations.
 */
public class CourseDAO {
    private final Connection conn;

    public CourseDAO(Connection conn) {
        if (conn == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        this.conn = conn;
    }

    /**
     * Adds a new course to the database.
     *
     * @param course The course object to be added.
     * @return The generated ID of the new course.
     * @throws SQLException If a database access error occurs.
     */
    public long addCourse(Course course) throws SQLException {
        if (course == null || course.getTitle() == null) {
            throw new IllegalArgumentException("Course and its title cannot be null");
        }
        if (course.getTeacherId() <= 0) {
            throw new IllegalArgumentException("Teacher ID must be positive");
        }
        String sql = "INSERT INTO course (title, teacher_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, course.getTitle());
            pstmt.setLong(2, course.getTeacherId());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            throw new SQLException("Failed to retrieve generated ID for the new course");
        }
    }

    /**
     * Updates an existing course in the database.
     *
     * @param course The course object with updated data.
     * @return True if the course was updated, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean updateCourse(Course course) throws SQLException {
        if (course == null || course.getTitle() == null) {
            throw new IllegalArgumentException("Course and its title cannot be null");
        }
        if (course.getTeacherId() <= 0) {
            throw new IllegalArgumentException("Teacher ID must be positive");
        }
        String sql = "UPDATE course SET title = ?, teacher_id = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getTitle());
            pstmt.setLong(2, course.getTeacherId());
            pstmt.setLong(3, course.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Deletes a course from the database.
     *
     * @param id The ID of the course to be deleted.
     * @return True if the course was deleted, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean deleteCourse(long id) throws SQLException {
        String sql = "DELETE FROM course WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Retrieves a course by ID from the database.
     *
     * @param id The ID of the course to be retrieved.
     * @return The course object if found, null otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public Course getCourseById(long id) throws SQLException {
        String sql = "SELECT * FROM course WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getLong("teacher_id")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Retrieves courses by title from the database.
     *
     * @param title The title of the courses to be retrieved.
     * @return A list of course objects with the given title.
     * @throws SQLException If a database access error occurs.
     */
    public List<Course> getCoursesByTitle(String title) throws SQLException {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course WHERE title = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getLong("teacher_id")
                    );
                    courses.add(course);
                }
            }
        }
        return courses;
    }

    /**
     * Retrieves all courses from the database.
     *
     * @return A list of all course objects.
     * @throws SQLException If a database access error occurs.
     */
    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Course course = new Course(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getLong("teacher_id")
                );
                courses.add(course);
            }
        }
        return courses;
    }
}