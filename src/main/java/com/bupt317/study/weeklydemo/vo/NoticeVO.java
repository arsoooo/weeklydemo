package com.bupt317.study.weeklydemo.vo;

public class NoticeVO {
    private Integer id;
    private String title;
    private String createTimeStr;
    private String allNames;
    private String notReadNames;
    // 个人公告显示才用，展示已读、未读
    private String status;

    @Override
    public String toString() {
        return "NoticeVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createTimeStr='" + createTimeStr + '\'' +
                ", allNames='" + allNames + '\'' +
                ", notReadNames='" + notReadNames + '\'' +
                ", status='" + status + '\'' +
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

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getAllNames() {
        return allNames;
    }

    public void setAllNames(String allNames) {
        this.allNames = allNames;
    }

    public String getNotReadNames() {
        return notReadNames;
    }

    public void setNotReadNames(String notReadNames) {
        this.notReadNames = notReadNames;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
