package com.softserve.academy.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//class config
@Configuration
class AppConfig {
    @Bean(name = "javaCourse")
    public Course javaCourse() {
        return new Course("Java");
    }

    @Bean(name = "pythonCourse")
    public Course pythoCcourse() {
        return new Course("Python");
    }

    @Bean
    public Student student(@Qualifier("javaCourse") Course course) {
        return new Student(course);
    }

}
