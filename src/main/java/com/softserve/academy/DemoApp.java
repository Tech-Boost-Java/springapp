package com.softserve.academy;

import hibernate.model.Course;
import hibernate.model.Enrollment;
import hibernate.model.Student;
import hibernate.model.Teacher;
import hibernate.repository.CourseRepository;
import hibernate.repository.EnrollmentRepository;
import hibernate.repository.StudentRepository;
import hibernate.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@AllArgsConstructor
@EnableJpaRepositories(basePackages = "hibernate.repository")
@EntityScan(basePackages = "hibernate.model")
@ComponentScan(basePackages = "hibernate")

public class DemoApp implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting application...");
        Student student = new Student().builder()
               // .id(1L) //error
                .firstName("Bob")
                .lastName("Green")
                .email("example@gmail.com")
                .group("CS-78")
                .build();
        studentRepository.save(student);
        Teacher teacher = Teacher.builder()
                .firstName("Ivan")
                .lastName("Brown")
                .email("ivan.petrenko@example.com")
                .position("Professor")
                .department("Computer Science")
                .build();


        teacherRepository.save(teacher);

        Course course = Course.builder()
                .title("Spring Boot Basics")
                .teacher(teacher)
                .build();

        courseRepository.save(course);

        System.out.println("Student saved: " + student);
        System.out.println("Teacher saved: " + teacher);
        System.out.println("Course saved: " + course);
        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .grade("A")
                .build();

        enrollmentRepository.save(enrollment);
        System.out.println("Enrollment saved: " + enrollment);
    }
}