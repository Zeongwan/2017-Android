package com.example.myapplication9;

/**
 * Created by 丁丁 on 2017/12/12 0012.
 */

public class User {
    private String name;
    private String blog;
    private int id;
    public String getName() {
        return name;
    }
    public String getBlog() {
        return blog;
    }
    public int getId() {
        return id;
    }
    public User(String name, String blog, int id) {
        this.name = name;
        this.blog = blog;
        this.id = id;
    }
}
