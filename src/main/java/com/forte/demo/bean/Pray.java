package com.forte.demo.bean;

public class Pray {

    private String qq;
    private Integer highqujian;
    private Integer highbaodi;
    private Integer customqujian;
    private Integer custombaodi;
    private Integer middlequjian;
    private Integer middlebaodi;
    private Integer specialqujian;
    private Integer specialbaodi;

    @Override
    public String toString() {
        return "Pray{" +
                "qq='" + qq + '\'' +
                ", highqujian=" + highqujian +
                ", highbaodi=" + highbaodi +
                ", customqujian=" + customqujian +
                ", custombaodi=" + custombaodi +
                ", middlequjian=" + middlequjian +
                ", middlebaodi=" + middlebaodi +
                ", specialqujian=" + specialqujian +
                ", specialbaodi=" + specialbaodi +
                '}';
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getHighqujian() {
        return highqujian;
    }

    public void setHighqujian(Integer highqujian) {
        this.highqujian = highqujian;
    }

    public Integer getHighbaodi() {
        return highbaodi;
    }

    public void setHighbaodi(Integer highbaodi) {
        this.highbaodi = highbaodi;
    }

    public Integer getCustomqujian() {
        return customqujian;
    }

    public void setCustomqujian(Integer customqujian) {
        this.customqujian = customqujian;
    }

    public Integer getCustombaodi() {
        return custombaodi;
    }

    public void setCustombaodi(Integer custombaodi) {
        this.custombaodi = custombaodi;
    }

    public Integer getMiddlequjian() {
        return middlequjian;
    }

    public void setMiddlequjian(Integer middlequjian) {
        this.middlequjian = middlequjian;
    }

    public Integer getMiddlebaodi() {
        return middlebaodi;
    }

    public void setMiddlebaodi(Integer middlebaodi) {
        this.middlebaodi = middlebaodi;
    }

    public Integer getSpecialqujian() {
        return specialqujian;
    }

    public void setSpecialqujian(Integer specialqujian) {
        this.specialqujian = specialqujian;
    }

    public Integer getSpecialbaodi() {
        return specialbaodi;
    }

    public void setSpecialbaodi(Integer specialbaodi) {
        this.specialbaodi = specialbaodi;
    }

    public Pray() {
    }

    public Pray(String qq, Integer highqujian, Integer highbaodi, Integer customqujian, Integer custombaodi, Integer middlequjian, Integer middlebaodi, Integer specialqujian, Integer specialbaodi) {
        this.qq = qq;
        this.highqujian = highqujian;
        this.highbaodi = highbaodi;
        this.customqujian = customqujian;
        this.custombaodi = custombaodi;
        this.middlequjian = middlequjian;
        this.middlebaodi = middlebaodi;
        this.specialqujian = specialqujian;
        this.specialbaodi = specialbaodi;
    }
}
