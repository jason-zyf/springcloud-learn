package com.copy.bean;

/**
 * @author zyting
 * @sinne 2020-08-10
 */
public class StudentShallow implements Cloneable{

    // 引用类型
    private SubjectShallow subject;
    //基础数据类型
    private String name;
    private int age;

    public SubjectShallow getSubject() {
        return subject;
    }
    public void setSubject(SubjectShallow subject) {
        this.subject = subject;
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
    /**
     *  重写clone()方法
     * @return
     */
    @Override
    public Object clone() {
        //浅拷贝
        try {
            // 直接调用父类的clone()方法
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    @Override
    public String toString() {
        return "[Student: " + this.hashCode() + ",subject:" + subject + ",name:" + name + ",age:" + age + "]";
    }
}
