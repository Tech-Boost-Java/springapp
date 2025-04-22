package com.softserve.academy.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component// Spring will create a bean of this class
public class Student {
    private final String name;
    private final Course course;//dependency

    // Constructor injection
    @Autowired
    public Student(Course course) {//we can add @Qualifier("JavaCourse") to specify which course we want to inject
        this.name = "Bob";
        this.course = course;
    }

    public void printInfo() {
        System.out.println("Student name: " + name);
        System.out.println("Course name: " + course.getName());
    }

}
