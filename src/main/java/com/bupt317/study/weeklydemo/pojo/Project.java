package com.bupt317.study.weeklydemo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bupt317.study.weeklydemo.config.StaticParams;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Project {

    // 设置insert时候id的生成
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;
    private String status;
    private Date createTime;
    private Date finishTime;
    private Date deadline;
    private String content;

    public Project() {
    }

    // addProject用
    public Project(String title, String status, Date createTime, Date deadline, String content) {
        this.title = title;
        this.status = status;
        this.createTime = createTime;
        this.deadline = deadline;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", finishTime=" + finishTime +
                ", deadline=" + deadline +
                ", content='" + content + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
