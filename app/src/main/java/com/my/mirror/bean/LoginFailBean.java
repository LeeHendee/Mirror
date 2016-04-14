package com.my.mirror.bean;

/**
 * Created by liangzaipan on 16/4/8.
 */
public class LoginFailBean {
    private String msg;

    public LoginFailBean(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
