package com.sysbot32.netptalk;

public class User {
    private String name;

    public User() {
        name = "user" + hashCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
