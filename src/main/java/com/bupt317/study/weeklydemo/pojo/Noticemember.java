package com.bupt317.study.weeklydemo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class Noticemember {
    // 设置insert时候id的生成
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer nid;
    private Integer uid;
    private String status;

    public Noticemember() {
    }

    public Noticemember(Integer nid, Integer uid, String status) {
        this.nid = nid;
        this.uid = uid;
        this.status = status;
    }


    @Override
    public String toString() {
        return "Noticemember{" +
                "id=" + id +
                ", nid=" + nid +
                ", uid=" + uid +
                ", status='" + status + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
