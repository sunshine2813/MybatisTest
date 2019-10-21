package com.sdhh.po;

public class Voucher {
    private Long id;

    private Integer year;

    private Integer month;

    private Integer number;

    private String company;

    private String imageurl;

    private String imagenames;

    private String attachurl;

    private String attachnames;

    private String detail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
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