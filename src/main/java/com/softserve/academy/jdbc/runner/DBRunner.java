package com.softserve.academy.jdbc.runner;

import com.softserve.academy.jdbc.dao.CourseDAO;
import com.softserve.academy.jdbc.dao.EnrollmentDAO;
import com.softserve.academy.jdbc.dao.StudentDAO;
import com.softserve.academy.jdbc.dao.TeacherDAO;
import com.softserve.academy.jdbc.example.Course;
import com.softserve.academy.jdbc.example.Enrollment;
import com.softserve.academy.jdbc.example.Student;
import com.softserve.academy.jdbc.example.Teacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Main class to demonstrate the functionality of the DAO classes.
 * This class serves as an entry point for the application and contains the main method.
 */
//public class DBRunner {
//    public static void main(String[] args) {
//
//        final String URL = "jdbc:h2:mem:university";
//        final String USER = "sa";
//        final String PASSWORD = "";
//
//        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);) {
//            // Ініціалізація DAO
//            StudentDAO studentDAO = new StudentDAO(conn);
//            TeacherDAO teacherDAO = new TeacherDAO(conn);
//            CourseDAO courseDAO = new CourseDAO(conn);
//            EnrollmentDAO enrollmentDAO = new EnrollmentDAO(conn);
//
//            // Додавання тестових даних
//            insertTestData(studentDAO, teacherDAO, courseDAO, enrollmentDAO);
//
//            // Виведення даних для перевірки
//            displayData(studentDAO, teacherDAO, courseDAO, enrollmentDAO);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void insertTestData(StudentDAO studentDAO, TeacherDAO teacherDAO,
//                                       CourseDAO courseDAO, EnrollmentDAO enrollmentDAO) throws SQLException {
//
//        long student1Id = studentDAO.addStudent(new Student(0L, "John Doe", "john.doe@example.com"));
//        long student2Id = studentDAO.addStudent(new Student(0L, "Jane Smith", "jane.smith@example.com"));
//
//
//        long teacher1Id = teacherDAO.addTeacher(new Teacher(0L, "Dr. Brown", "Computer Science"));
//        long teacher2Id = teacherDAO.addTeacher(new Teacher(0L, "Prof. Green", "Mathematics"));
//
//        long course1Id = courseDAO.addCourse(new Course(0L, "Introduction to Programming", teacher1Id));
//        long course2Id = courseDAO.addCourse(new Course(0L, "Calculus I", teacher2Id));
//
//
//        enrollmentDAO.addEnrollment(new Enrollment(0L, student1Id, course1Id, "A"));
//        enrollmentDAO.addEnrollment(new Enrollment(0L, student1Id, course2Id, "B+"));
//        enrollmentDAO.addEnrollment(new Enrollment(0L, student2Id, course1Id, "C"));
//    }
//
//    private static void displayData(StudentDAO studentDAO, TeacherDAO teacherDAO,
//                                    CourseDAO courseDAO, EnrollmentDAO enrollmentDAO) throws SQLException {
//
//        System.out.println("Students:");
//        for (Student student : studentDAO.getAllStudents()) {
//            System.out.println(student);
//        }
//
//        System.out.println("\nTeachers:");
//        for (Teacher teacher : teacherDAO.getAllTeachers()) {
//            System.out.println(teacher);
//        }
//
//        System.out.println("\nCourses:");
//        for (Course course : courseDAO.getAllCourses()) {
//            System.out.println(course);
//        }
//
//        System.out.println("\nEnrollments:");
//        for (Enrollment enrollment : enrollmentDAO.getAllEnrollments()) {
//            System.out.println(enrollment);
//        }
//    }
//}
//
public class DBRunner {
    public static void main(String[] args) {

        String jdbcUrl = "jdbc:h2:mem:university;DB_CLOSE_DELAY=-1";//in-memory database
        // String jdbcUrl = "jdbc:h2:~/university;DB_CLOSE_DELAY=-1";// if you want to use file-based database
        String username = "sa";
        String password = "";

        // Diagnostic message to check if the H2 driver is loaded
//        try {
//            Class.forName("org.h2.Driver");
//            System.out.println("H2 Driver successfully loaded.");
//        } catch (ClassNotFoundException e) {
//            System.err.println("H2 Driver not found. Please ensure the H2 dependency is added to your project.");
//            e.printStackTrace();
//            return;
//        }

        // Connecting to the H2 database
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Successfully connected to H2 database: " + jdbcUrl);

            // Creating tables
            createTables(conn);

            // Initializing DAO classes
            StudentDAO studentDAO = new StudentDAO(conn);
            TeacherDAO teacherDAO = new TeacherDAO(conn);
            CourseDAO courseDAO = new CourseDAO(conn);
            EnrollmentDAO enrollmentDAO = new EnrollmentDAO(conn);

            // Adding test data
            insertTestData(studentDAO, teacherDAO, courseDAO, enrollmentDAO);

            // Outputting data to verify the operations
            displayData(studentDAO, teacherDAO, courseDAO, enrollmentDAO);

        } catch (SQLException e) {
            System.err.println("Failed to connect to the database or execute operations.");
            e.printStackTrace();
        }
    }

    private static void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Creating the student table
            stmt.executeUpdate("CREATE TABLE student (" +
                    "id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "email VARCHAR(255) NOT NULL" +
                    ")");

            // Creating the teacher table
            stmt.executeUpdate("CREATE TABLE teacher (" +
                    "id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "department VARCHAR(255) NOT NULL" +
                    ")");

            // Creating the course table
            stmt.executeUpdate("CREATE TABLE course (" +
                    "id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
                    "title VARCHAR(255) NOT NULL, " +
                    "teacher_id BIGINT NOT NULL, " +
                    "FOREIGN KEY (teacher_id) REFERENCES teacher(id)" +
                    ")");

            // Creating the enrollment table
            stmt.executeUpdate("CREATE TABLE enrollment (" +
                    "id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
                    "student_id BIGINT NOT NULL, " +
                    "course_id BIGINT NOT NULL, " +
                    "grade VARCHAR(255), " +
                    "FOREIGN KEY (student_id) REFERENCES student(id), " +
                    "FOREIGN KEY (course_id) REFERENCES course(id)" +
                    ")");
        }
    }

    private static void insertTestData(StudentDAO studentDAO, TeacherDAO teacherDAO,
                                       CourseDAO courseDAO, EnrollmentDAO enrollmentDAO) throws SQLException {
        // Addition of students
        long student1Id = studentDAO.addStudent(new Student(0L, "John Doe", "john.doe@example.com"));
        long student2Id = studentDAO.addStudent(new Student(0L, "Jane Smith", "jane.smith@example.com"));

        // Addition of teachers
        long teacher1Id = teacherDAO.addTeacher(new Teacher(0L, "Dr. Brown", "Computer Science"));
        long teacher2Id = teacherDAO.addTeacher(new Teacher(0L, "Prof. Green", "Mathematics"));

        // Addition of courses
        long course1Id = courseDAO.addCourse(new Course(0L, "Introduction to Programming", teacher1Id));
        long course2Id = courseDAO.addCourse(new Course(0L, "Calculus I", teacher2Id));

        // Addition of enrollments
        enrollmentDAO.addEnrollment(new Enrollment(0L, student1Id, course1Id, "A"));
        enrollmentDAO.addEnrollment(new Enrollment(0L, student1Id, course2Id, "B+"));
        enrollmentDAO.addEnrollment(new Enrollment(0L, student2Id, course1Id, "A-"));
    }

    private static void displayData(StudentDAO studentDAO, TeacherDAO teacherDAO,
                                    CourseDAO courseDAO, EnrollmentDAO enrollmentDAO) throws SQLException {
        //Outputting all students
        System.out.println("Students:");
        for (Student student : studentDAO.getAllStudents()) {
            System.out.println(student);
        }

        // Outputting all teachers
        System.out.println("\nTeachers:");
        for (Teacher teacher : teacherDAO.getAllTeachers()) {
            System.out.println(teacher);
        }

        // Outputting all courses
        System.out.println("\nCourses:");
        for (Course course : courseDAO.getAllCourses()) {
            System.out.println(course);
        }

        //Outputting all enrollments
        System.out.println("\nEnrollments:");
        for (Enrollment enrollment : enrollmentDAO.getAllEnrollments()) {
            System.out.println(enrollment);
        }
    }
}