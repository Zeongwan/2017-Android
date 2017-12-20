package com.example.myapplication9;

/**
 * Created by 丁丁 on 2017/12/12 0012.
 */

public class Repos {
    private String name;
    private String descr;
    private String language;
    public String getName() {
        return name;
    }
    public String getDescr() {
        return descr;
    }
    public String getLanguage() {
        return language;
    }
    public Repos(String name, String descr, String language) {
        this.name = name;
        this.descr = descr;
        this.language = language;
    }
}
