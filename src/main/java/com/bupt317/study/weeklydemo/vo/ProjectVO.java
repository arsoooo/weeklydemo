package com.bupt317.study.weeklydemo.vo;

public class ProjectVO {
    private Integer id;
    private String title;
    private String projectTimeStr; // 开始时间+描述
    private String deadlineTimeStr;
    private String finishTimeStr;
    private String projectState;
    private String names;
    private String content;

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

    public String getProjectTimeStr() {
        return projectTimeStr;
    }

    public void setProjectTimeStr(String projectTimeStr) {
        this.projectTimeStr = projectTimeStr;
    }

    public String getDeadlineTimeStr() {
        return deadlineTimeStr;
    }

    public void setDeadlineTimeStr(String deadlineTimeStr) {
        this.deadlineTimeStr = deadlineTimeStr;
    }

    public String getFinishTimeStr() {
        return finishTimeStr;
    }

    public void setFinishTimeStr(String finishTimeStr) {
        this.finishTimeStr = finishTimeStr;
    }

    public String getProjectState() {
        return projectState;
    }

    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
