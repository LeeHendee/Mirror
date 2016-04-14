package com.my.mirror.bean;

/**
 * Created by liangzaipan on 16/4/6.
 */
public class RegisterFailBean {
    private String msg;

    public RegisterFailBean(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
