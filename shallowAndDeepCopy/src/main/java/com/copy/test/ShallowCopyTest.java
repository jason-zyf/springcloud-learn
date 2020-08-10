package com.copy.test;

import com.copy.bean.StudentShallow;
import com.copy.bean.SubjectShallow;

/**
 * @author zyting
 * @sinne 2020-08-10
 * 浅拷贝测试类
 */
public class ShallowCopyTest {

    public static void main(String[] args) {
        SubjectShallow subject = new SubjectShallow("yuwen");
        StudentShallow studentA = new StudentShallow();
        studentA.setSubject(subject);
        studentA.setName("LYnn");
        studentA.setAge(20);
        StudentShallow studentB = (StudentShallow) studentA.clone();
        studentB.setName("Lily");
        studentB.setAge(18);
        SubjectShallow subjectB = studentB.getSubject();
        subjectB.setName("lishi");
        System.out.println("studentA:"+studentA.toString());
        System.out.println("studentB:"+studentB.toString());
    }
}
