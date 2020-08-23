package com.bupt317.study.weeklydemo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.security.PrivateKey;

public class Projectmember {

    // 设置insert时候id的生成
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer pid;
    private Integer uid;
    private String uname;


    public Projectmember() {
    }

    public Projectmember(Integer pid, Integer uid, String uname) {
        this.pid = pid;
        this.uid = uid;
        this.uname = uname;
    }

    @Override
    public String toString() {
        return "Projectmember{" +
                "id=" + id +
                ", pid=" + pid +
                ", uid=" + uid +
                ", uname='" + uname + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
