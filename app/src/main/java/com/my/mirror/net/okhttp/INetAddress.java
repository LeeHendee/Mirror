package com.my.mirror.net.okhttp;

/**
 * Created by dllo on 16/3/30.
 */
public interface INetAddress {
    String BEGIN_URL = "http://api101.test.mirroreye.cn/";

    String DEVICE = "1";
    String DEVICE_TYPE = "device_type";
    String VERSION = "version";
    String VERSION_VALUE = "1.0.1";
    String DEVICE_REUSE = "2";
    String GOOD_TYPE = "good_type";
    String STORY_ID = "story_id";
    String CATEGORY_ID = "category_id";
    String TOKEN = "token";
    String USERNAME = "username";
    String CELLPHONE = "cellphone";
    String ADDR_INFO = "addr_info";
    String ADDR_ID = "addr_id";
    String PHONE_NUMBER = "phone_number";
    String PASSWORD = "password";
    String PHONENUMBER = "phone number";
    String NUMBER = "number";


    //
    String STARTED_IMG = "index.php/index/started_img";

    //商品详情
    String PRODUCT_LIST = "index.php/products/goods_list";

    //菜单列表
    String CATEGORY_LIST = "index.php/products/category_list";
    //String CATEGORY_LIST = "index.php/index/menu_list";

    //首页商品
    String GOODS_LIST = "index.php/products/goods_list";

    //专题分享
    String STORY_LIST = "index.php/story/story_list";

    //专题分享 二级页面
    String  INFO = "index.php/story/info";

    //注册
    String REG = "index.php/user/reg";

    //发送验证码
    String SEND_CODE= "index.php/user/send_code";

    //登录
    String LOGIN = "index.php/user/login";

    //添加地址
    String ADD_DRESS = "index.php/user/add_address";

    //删除地址
    String EDIT_ADDRESS = "index.php/user/edit_address";

    String ADDRESS_LIST = "index.php/user/address_list";

    String DEL_ADDRESS = "index.php/user/del_address";

    String MR_ADDRESS = "index.php/user/mr_address";

}

