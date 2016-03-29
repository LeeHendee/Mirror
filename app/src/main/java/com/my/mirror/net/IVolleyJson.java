package com.my.mirror.net;

import org.json.JSONObject;

/**
 * Created by dllo on 16/3/29.
 */
public interface IVolleyJson {
    void succeedListener(JSONObject jsonObject);
    void failedListener(String s);
}
