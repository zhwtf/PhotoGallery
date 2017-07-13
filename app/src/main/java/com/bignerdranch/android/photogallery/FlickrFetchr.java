package com.bignerdranch.android.photogallery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhenghao on 2017-07-13.
 */

//建立一个网络请求的函数

public class FlickrFetchr {
    // FlickrFetchr.java
    // 参数是 url 字符串，并且需要抛出 IO 错误
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            // 建立两个流对象
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 使用 getInputStream() 方法时才会真正发送 GET 请求
            // 如果要使用 POST 请求，需要调用 getOutputStream()
            InputStream in = connection.getInputStream();
            // 如果连接失败就抛出错误
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                            urlSpec);
            }

            // 建立一个计数器
            int bytesRead = 0;
            // 建立一个缓存 buffer
            byte[] buffer = new byte[1024];
            // 用 InputStream.read 将数据读取到 buffer 中，
            // 然后写到 OutputStream 中
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            // 之后一定要关闭 OutputStream
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        // 将结果转换成 String
        return new String(getUrlBytes(urlSpec));
    }
}
