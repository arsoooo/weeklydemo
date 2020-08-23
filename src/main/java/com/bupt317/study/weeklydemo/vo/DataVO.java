package com.bupt317.study.weeklydemo.vo;

import com.bupt317.study.weeklydemo.config.StaticParams;

public class DataVO {
    private Integer code;
    private String msg;
    private long count;
    private Object data;


    public static DataVO success() {
        return new DataVO(StaticParams.SUCCESS_CODE);
    }
    public static DataVO success(Object data) {
        return new DataVO(StaticParams.SUCCESS_CODE,"",data);
    }
    public static DataVO fail(String message) {
        return new DataVO(StaticParams.FAIL_CODE, message);
    }

    public DataVO() {
    }

    public DataVO(Integer code) {
        this.code = code;
    }

    public DataVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public DataVO(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public DataVO(Integer code, String msg, long count, Object data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
