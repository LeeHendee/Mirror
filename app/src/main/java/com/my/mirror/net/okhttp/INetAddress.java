package com.my.mirror.net.okhttp;

/**
 * Created by dllo on 16/3/30.
 */
public interface INetAddress {
    String BEGIN_URL = "http://api101.test.mirroreye.cn/";

    String DEVICE = "iPhone6";
    String DEVICE_TYPE = "device_type";
    String DEVICE_REUSE = "2";
    String GOOD_TYPE = "good_type";

    //商品详情
    String PRODUCT_LIST = "index.php/products/goods_list";

    //菜单列表
    String CATEGORY_LIST = "index.php/products/category_list";

    //首页商品
    String GOODS_LIST = "index.php/products/goods_list";

    //专题分享
    String STORY_LIST = "index.php/story/story_list";

}

