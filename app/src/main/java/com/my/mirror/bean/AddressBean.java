package com.my.mirror.bean;

import java.util.List;

/**
 * Created by liangzaipan on 16/4/18.
 */
public class AddressBean {

    /**
     * result : 1
     * msg :
     * data : {"pagination":{"first_time":"1460967979","last_time":"","has_more":"2"},"list":[{"addr_id":"1096","zip_code":"","username":"234","cellphone":"234","addr_info":"234","if_moren":"2","city":""},{"addr_id":"1095","zip_code":"","username":"123","cellphone":"123","addr_info":"123","if_moren":"1","city":""}]}
     */

    private String result;
    private String msg;
    /**
     * pagination : {"first_time":"1460967979","last_time":"","has_more":"2"}
     * list : [{"addr_id":"1096","zip_code":"","username":"234","cellphone":"234","addr_info":"234","if_moren":"2","city":""},{"addr_id":"1095","zip_code":"","username":"123","cellphone":"123","addr_info":"123","if_moren":"1","city":""}]
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
        /**
         * first_time : 1460967979
         * last_time :
         * has_more : 2
         */

        private PaginationBean pagination;
        /**
         * addr_id : 1096
         * zip_code :
         * username : 234
         * cellphone : 234
         * addr_info : 234
         * if_moren : 2
         * city :
         */

        private List<ListBean> list;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PaginationBean {
            private String first_time;
            private String last_time;
            private String has_more;

            public String getFirst_time() {
                return first_time;
            }

            public void setFirst_time(String first_time) {
                this.first_time = first_time;
            }

            public String getLast_time() {
                return last_time;
            }

            public void setLast_time(String last_time) {
                this.last_time = last_time;
            }

            public String getHas_more() {
                return has_more;
            }

            public void setHas_more(String has_more) {
                this.has_more = has_more;
            }
        }

        public static class ListBean {
            private String addr_id;
            private String zip_code;
            private String username;
            private String cellphone;
            private String addr_info;
            private String if_moren;
            private String city;

            public String getAddr_id() {
                return addr_id;
            }

            public void setAddr_id(String addr_id) {
                this.addr_id = addr_id;
            }

            public String getZip_code() {
                return zip_code;
            }

            public void setZip_code(String zip_code) {
                this.zip_code = zip_code;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getCellphone() {
                return cellphone;
            }

            public void setCellphone(String cellphone) {
                this.cellphone = cellphone;
            }

            public String getAddr_info() {
                return addr_info;
            }

            public void setAddr_info(String addr_info) {
                this.addr_info = addr_info;
            }

            public String getIf_moren() {
                return if_moren;
            }

            public void setIf_moren(String if_moren) {
                this.if_moren = if_moren;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }
        }
    }
}
