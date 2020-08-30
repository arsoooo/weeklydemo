package com.bupt317.study.weeklydemo.vo;

public class NoticeVO {
    private Integer id;
    private String title;
    private String createTimeStr;
    private String allNames;
    private String notReadNames;

    @Override
    public String toString() {
        return "NoticeVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createTimeStr='" + createTimeStr + '\'' +
                ", userNames='" + allNames + '\'' +
                ", unReadNames='" + notReadNames + '\'' +
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
}
