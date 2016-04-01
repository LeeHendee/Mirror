package com.my.mirror.net;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by dllo on 16/3/29.
 */
public class VolleyHelper {
    private Context context;
    private RequestQueue queue;

    public VolleyHelper(RequestQueue queue) {
        SingleQueue singleQueue = SingleQueue.getInstance(context);
        queue = singleQueue.getRequestQueue(context);
    }
    //String 方式进行网络请求;
    public void getStringDataByGet(String url, final Map head){
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (head!=null){
                    return head;
                }
                return super.getHeaders();
            }
        };
        queue.add(request);
    }
    //Object 方式进行网络请求;
    public void getJsonObjectDataByGet(String url, final Map head){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (head!=null){
                    return head;
                }
                return super.getHeaders();
            }
        };
        queue.add(request);
    }
    //ImageRequest方式请求网络图片;
    public void getImageFromNet(String url){
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

            }
        }, 0, 0, Bitmap.Config.ARGB_4444, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    //POST请求方式,数据类型是String:
    public void getStringDataByPost(String url, final Map head, final Map params, final IVolleyString iVolleyString) {
        final StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                iVolleyString.succeedListener(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iVolleyString.failedListener(error.toString());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (head != null) {
                    return head;
                }
                return super.getHeaders();
            }
            //因为Post类型请求多一个参数拼接;
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }
        };
        queue.add(request);
    }

}
