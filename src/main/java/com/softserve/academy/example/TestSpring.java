package com.softserve.academy.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        // Create a new ClassPathXmlApplicationContext object to load the Spring configuration file
        //from a classpath resource named "applicationContext.xml"
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        TestBean testBean = context.getBean("testBean", TestBean.class);//name of the bean in the XML file

        System.out.println(testBean.getName());//output the name property of the TestBean object

        context.close();//make sure to close the context to release resources
    }
}