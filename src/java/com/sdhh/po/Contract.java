package com.sdhh.po;

import java.math.BigDecimal;

public class Contract {
    private Long id;

    private String name;

    private String type;

    private Integer year;

    private Integer month;

    private String number;

    private String partb;

    private BigDecimal money;

    private String department;

    private String imageurl;

    private String imagenames;

    private String attachurl;

    private String attachnames;

    private String detail;

    public Contract() {
    }

    ;

    public Contract(String name, String type, String number, int year, int month, String partb, BigDecimal money, String department, String detail) {
        this.name = name;
        this.type = type;
        this.number = number;
        this.year = year;
        this.month = month;
        this.partb = partb;
        this.money = money;
        this.department = department;
        this.detail = detail;
        this.imageurl = "";
        this.imagenames = "";
        this.attachurl = "";
        this.attachnames = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getPartb() {
        return partb;
    }

    public void setPartb(String partb) {
        this.partb = partb == null ? null : partb.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl == null ? null : imageurl.trim();
    }

    public String getImagenames() {
        return imagenames;
    }

    public void setImagenames(String imagenames) {
        this.imagenames = imagenames == null ? null : imagenames.trim();
    }

    public String getAttachurl() {
        return attachurl;
    }

    public void setAttachurl(String attachurl) {
        this.attachurl = attachurl == null ? null : attachurl.trim();
    }

    public String getAttachnames() {
        return attachnames;
    }

    public void setAttachnames(String attachnames) {
        this.attachnames = attachnames == null ? null : attachnames.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }
}