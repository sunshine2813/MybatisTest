package com.sdhh.po;

public class ContractType {
    private Long id;

    private String typename;

    public ContractType() {
    }

    public ContractType(String typename) {
        this.typename = typename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
    }
}