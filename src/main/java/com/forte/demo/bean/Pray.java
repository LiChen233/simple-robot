package com.forte.demo.bean;

public class Pray {

    private String qq;
    private Integer qujian;
    private Integer baodi;

    @Override
    public String toString() {
        return "Pray{" +
                "qq='" + qq + '\'' +
                ", qujian=" + qujian +
                ", baodi=" + baodi +
                '}';
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getQujian() {
        return qujian;
    }

    public void setQujian(Integer qujian) {
        this.qujian = qujian;
    }

    public Integer getBaodi() {
        return baodi;
    }

    public void setBaodi(Integer baodi) {
        this.baodi = baodi;
    }

    public Pray(String qq, Integer qujian, Integer baodi) {
        this.qq = qq;
        this.qujian = qujian;
        this.baodi = baodi;
    }

    public Pray() {
    }
}
