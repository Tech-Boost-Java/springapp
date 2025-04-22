package com.softserve.academy.firstapp;

//class Student is depending on class Course
 class Student {
    private String name;
    private Course course;//dependency

    public Student(String name) {
        this.name = name;
        this.course = new Course("Java");
    }

    public void printInfo() {
        System.out.println("Student name: " + name);
        System.out.println("Course name: " + course.getName());
    }

}
