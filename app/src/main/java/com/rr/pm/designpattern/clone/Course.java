package com.rr.pm.designpattern.clone;

import java.io.Serializable;

/**
 * 成绩
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/24 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class Course implements Serializable, Cloneable {
    private static final long serialVersionUID = -239015628217431194L;

    private String grade;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Course(this);
    }

    public Course(String grade) {
        this.grade = grade;
    }

    public Course(Course course) {
        this.grade = course.grade;
    }

    @Override
    public String toString() {
        return "Course{" +
                "grade='" + grade + '\'' +
                '}';
    }
}
