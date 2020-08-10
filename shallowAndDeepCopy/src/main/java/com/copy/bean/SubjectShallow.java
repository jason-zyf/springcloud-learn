package com.copy.bean;

/**
 * @author zyting
 * @sinne 2020-08-10
 * 浅拷贝类
 */
public class SubjectShallow {

    private String name;

    public SubjectShallow(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[Subject: " + this.hashCode() + ",name:" + name + "]";
    }
}
