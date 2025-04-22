package com.softserve.academy.example;

/**
 * TestBean is a simple Java class that represents a bean with a name property.
 */
public class TestBean {
    private String name;

    public TestBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}