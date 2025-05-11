package com.example.lab3;

public class Student {
    private int id;
    private String name;
    private String lop;

    public Student(int id, String name, String lop) {
        this.id = id;
        this.name = name;
        this.lop = lop;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLop() {
        return lop;
    }
}
