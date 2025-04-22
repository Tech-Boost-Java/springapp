package com.softserve.academy.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Course {
    private final String name;

    @Autowired
    public Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
