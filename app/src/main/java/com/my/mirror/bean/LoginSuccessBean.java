package com.my.mirror.bean;

/**
 * Created by liangzaipan on 16/4/8.
 */
public class LoginSuccessBean {

    /**
     * result : 1
     * msg :
     * data : {"token":"d40a6a56e4b5cc45c7aa03c76ec8e6f6","uid":"58"}
     */

    private String result;
    private String msg;
    /**
     * token : d40a6a56e4b5cc45c7aa03c76ec8e6f6
     * uid : 58
     */

    private DataBean data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String token;
        private String uid;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
