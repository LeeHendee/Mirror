package com.my.mirror.bean;

import java.util.List;

/**
 * Created by liangzaipan on 16/4/18.
 */
public class AddressBean {

    /**
     * result : 1
     * msg :
     * data : {"pagination":{"first_time":"","last_time":"","has_more":"2"},"list":[]}
     */

    private String result;
    private String msg;
    /**
     * pagination : {"first_time":"","last_time":"","has_more":"2"}
     * list : []
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
         * first_time :
         * last_time :
         * has_more : 2
         */

        private PaginationBean pagination;
        private List<?> list;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public List<?> getList() {
            return list;
        }

        public void setList(List<?> list) {
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
    }
}
