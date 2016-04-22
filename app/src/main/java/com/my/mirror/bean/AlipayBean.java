package com.my.mirror.bean;

/**
 * Created by dllo on 16/4/21.
 */
public class AlipayBean {

    /**
     * result : 1
     * msg :
     * data : {"address":{"city":"","cellphone":"11111122212","addr_info":"hh","addr_id":"1089","zip_code":"","username":"jjjhhhhhhhhhh"},"goods":{"goods_name":"GENTLE MONSTER","des":"BIG BULLY 飛行員太陽鏡","book_copy":"文案（订购商品）文案（订购商品）文案（订购商品）文案（订购商品）","price":"1300","goods_num":"","pic":"http://7xprhi.com2.z0.glb.qiniucdn.com/gmsliver155cbc9a39c911ee63efda10378130330.jpg"},"order_id":"1460960308PMn","if_ordain":"1"}
     */
    private String result;
    private String msg;
    private DataEntity data;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public class DataEntity {
        /**
         * address : {"city":"","cellphone":"11111122212","addr_info":"hh","addr_id":"1089","zip_code":"","username":"jjjhhhhhhhhhh"}
         * goods : {"goods_name":"GENTLE MONSTER","des":"BIG BULLY 飛行員太陽鏡","book_copy":"文案（订购商品）文案（订购商品）文案（订购商品）文案（订购商品）","price":"1300","goods_num":"","pic":"http://7xprhi.com2.z0.glb.qiniucdn.com/gmsliver155cbc9a39c911ee63efda10378130330.jpg"}
         * order_id : 1460960308PMn
         * if_ordain : 1
         */
        private AddressEntity address;
        private GoodsEntity goods;
        private String order_id;
        private String if_ordain;

        public void setAddress(AddressEntity address) {
            this.address = address;
        }

        public void setGoods(GoodsEntity goods) {
            this.goods = goods;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public void setIf_ordain(String if_ordain) {
            this.if_ordain = if_ordain;
        }

        public AddressEntity getAddress() {
            return address;
        }

        public GoodsEntity getGoods() {
            return goods;
        }

        public String getOrder_id() {
            return order_id;
        }

        public String getIf_ordain() {
            return if_ordain;
        }

        public class AddressEntity {
            /**
             * city :
             * cellphone : 11111122212
             * addr_info : hh
             * addr_id : 1089
             * zip_code :
             * username : jjjhhhhhhhhhh
             */
            private String city;
            private String cellphone;
            private String addr_info;
            private String addr_id;
            private String zip_code;
            private String username;
            public void setCity(String city) {
                this.city = city;
            }

            public void setCellphone(String cellphone) {
                this.cellphone = cellphone;
            }

            public void setAddr_info(String addr_info) {
                this.addr_info = addr_info;
            }

            public void setAddr_id(String addr_id) {
                this.addr_id = addr_id;
            }

            public void setZip_code(String zip_code) {
                this.zip_code = zip_code;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getCity() {
                return city;
            }

            public String getCellphone() {
                return cellphone;
            }

            public String getAddr_info() {
                return addr_info;
            }

            public String getAddr_id() {
                return addr_id;
            }

            public String getZip_code() {
                return zip_code;
            }

            public String getUsername() {
                return username;
            }
        }

        public class GoodsEntity {
            /**
             * goods_name : GENTLE MONSTER
             * des : BIG BULLY 飛行員太陽鏡
             * book_copy : 文案（订购商品）文案（订购商品）文案（订购商品）文案（订购商品）
             * price : 1300
             * goods_num :
             * pic : http://7xprhi.com2.z0.glb.qiniucdn.com/gmsliver155cbc9a39c911ee63efda10378130330.jpg
             */
            private String goods_name;
            private String des;
            private String book_copy;
            private String price;
            private String goods_num;
            private String pic;

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public void setBook_copy(String book_copy) {
                this.book_copy = book_copy;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setGoods_num(String goods_num) {
                this.goods_num = goods_num;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public String getDes() {
                return des;
            }

            public String getBook_copy() {
                return book_copy;
            }

            public String getPrice() {
                return price;
            }

            public String getGoods_num() {
                return goods_num;
            }

            public String getPic() {
                return pic;
            }
        }
    }
}
