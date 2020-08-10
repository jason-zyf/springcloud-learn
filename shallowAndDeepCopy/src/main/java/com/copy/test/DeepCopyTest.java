package com.copy.test;

import com.copy.bean.StudentDeep;
import com.copy.bean.SubjectDeep;

/**
 * @author zyting
 * @sinne 2020-08-10
 */
public class DeepCopyTest {

    public static void main(String[] args) {
        SubjectDeep subject = new SubjectDeep("yuwen");
        StudentDeep studentA = new StudentDeep();
        studentA.setSubject(subject);
        studentA.setName("LYnn");
        studentA.setAge(20);
        StudentDeep studentB = (StudentDeep) studentA.clone();
        studentB.setName("Lily");
        studentB.setAge(18);
        SubjectDeep subjectB = studentB.getSubject();
        subjectB.setName("lishi");
        System.out.println("studentA:"+studentA.toString());
        System.out.println("studentB:"+studentB.toString());
    }
}
