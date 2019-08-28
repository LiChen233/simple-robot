package com.forte.demo.bean;

public class Person {

    private String qq;
    private Integer star;

    @Override
    public String toString() {
        return "Person{" +
                "qq='" + qq + '\'' +
                ", star=" + star +
                '}';
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Person() {
    }

    public Person(String qq, Integer star) {
        this.qq = qq;
        this.star = star;
    }
}
