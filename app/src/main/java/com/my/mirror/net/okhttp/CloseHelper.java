package com.my.mirror.net.okhttp;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by dllo on 16/3/30.
 */
public class CloseHelper {
    public static final void close(Closeable closeable) {
        try {
            closeable.close();
            closeable = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
