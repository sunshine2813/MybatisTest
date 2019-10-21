package com.sdhh.po;

public class Finance {
    private Long id;

    private String type;

    private String name;

    private String username;

    private String imageurl;

    private String imagenames;

    private String attachurl;

    private String attachnames;

    private String detail;

    public Finance() {
    }

    ;

    public Finance(String name, String type, String username, String detail) {
        this.type = type;
        this.name = name;
        this.username = username;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
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