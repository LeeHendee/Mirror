package com.my.mirror.bean;

/**
 * Created by liangzaipan on 16/4/8.
 */
public class RegisterSuccessBean {
    private String result,data;

    public RegisterSuccessBean(String result, String data) {
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
