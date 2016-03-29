package com.my.mirror.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dllo on 16/3/29.
 */
public class SingleQueue {
    private static SingleQueue queue;
    private RequestQueue requestQueue;

    private SingleQueue(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public RequestQueue getRequestQueue(Context context) {
        return requestQueue;
    }

    public static SingleQueue getInstance(Context context){
        if (queue == null){
            synchronized (SingleQueue.class){
                if (queue == null){
                    queue = new SingleQueue(context);

                }
            }
        }
        return queue;
    }
}
