package com.rr.pm.designpattern.clone;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 原型模式：用原型实例指定创建对象的种类,并通过拷贝这些原型创建新的对象
 * 步骤:
 * 1. 实现Clonneable接口
 * 2. 重写clone方法
 * 拷贝: 深拷贝和浅拷贝
 * 浅拷贝在引用类型拷贝上存在问题，浅拷贝拷贝的指示引用类型的引用，
 * 在堆内存中这些引用指向的是第一块堆内存，所以一个对象修改其他对象也会修改。
 * 所以存在引用类型拷贝要使用深拷贝.
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/24 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class Person implements Serializable, Cloneable {
    private static final long serialVersionUID = 8811235006176709313L;

    private String name;
    private String sex;
    private int age;
    private ArrayList<Course> courses;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Person(this);
    }

    public Person(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public Person(Person p) {
        this.name = p.name;
        this.sex = p.sex;
        this.age = p.age;
        this.courses = (ArrayList<Course>) p.courses.clone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", courses=" + courses +
                '}';
    }
}
