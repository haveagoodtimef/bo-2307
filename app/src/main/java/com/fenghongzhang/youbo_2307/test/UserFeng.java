package com.fenghongzhang.youbo_2307.test;

import javax.inject.Inject;

public class UserFeng {
    private String name;
    private int age;


    @Inject
    public UserFeng() {
        super();
    }

    public UserFeng(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return "User [name=" + name + ", age=" + age + "]";
    }
}
