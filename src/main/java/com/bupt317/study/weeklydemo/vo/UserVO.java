package com.bupt317.study.weeklydemo.vo;

public class UserVO {
    private Integer id;
    private String name;
    private String permStr;
    private String email;
    private String phone;
    private String other;

    public UserVO() {
    }

    public UserVO(String permStr) {
        this.permStr = permStr;
    }

    public UserVO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserVO(Integer id, String name, String permStr) {
        this.id = id;
        this.name = name;
        this.permStr = permStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermStr() {
        return permStr;
    }

    public void setPermStr(String permStr) {
        this.permStr = permStr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
