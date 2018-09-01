package com.zaiym.rpc.domian;

import java.io.Serializable;

public class User implements Serializable{

    private static final long serialVersionUID = -733534950621034736L;

    private String userName;

    private int age;

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}