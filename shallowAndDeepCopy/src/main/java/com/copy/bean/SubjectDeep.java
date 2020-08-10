package com.copy.bean;

/**
 * @author zyting
 * @sinne 2020-08-10
 */
public class SubjectDeep implements Cloneable{

    private String name;

    public SubjectDeep(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        //Subject 如果也有引用类型的成员属性，也应该和 Student 类一样实现
        return super.clone();
    }

    @Override
    public String toString() {
        return "[Subject: " + this.hashCode() + ",name:" + name + "]";
    }
}
