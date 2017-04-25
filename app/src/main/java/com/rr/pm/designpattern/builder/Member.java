package com.rr.pm.designpattern.builder;

import java.io.Serializable;

/**
 * Builder模式 --> 建造者模式
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/24 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class Member implements Serializable {
    private static final long serialVersionUID = -8661774348559884297L;

    private String name;
    private int age;
    private String sex;
    private String certNo;

    public Member(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.sex = builder.sex;
        this.certNo = builder.certNo;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", certNo='" + certNo + '\'' +
                '}';
    }

    public static class Builder {
        private String name;
        private int age;
        private String sex;
        private String certNo;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setSex(String sex) {
            this.sex = sex;
            return this;
        }

        public Builder setCertNo(String certNo) {
            this.certNo = certNo;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }
}
