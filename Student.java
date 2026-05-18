package com.classrep.model;

public class Student {
    private final String registerNumber;
    private final String name;

    public Student(String registerNumber, String name) {
        this.registerNumber = registerNumber;
        this.name = name;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public String getName() {
        return name;
    }
}
