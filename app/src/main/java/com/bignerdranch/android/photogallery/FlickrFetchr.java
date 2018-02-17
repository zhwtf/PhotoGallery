package com.bignerdranch.android.photogallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenghao on 2017-07-13.
 */

//建立一个网络请求的函数

public class FlickrFetchr {

    private static final String TAG = "FlickrFetchr";
    private static final String API_KEY = "10afabcf7a13b4dea51f2126d7139526";
    private static final String FETCH_RECENTS_METHOD = "flickr.photos.getRecent";
    private static final String SEARCH_METHOD = "flickr.photos.search";
    private static final Uri ENDPOINT = Uri
            .parse("https://api.flickr.com/services/rest/")
            .buildUpon()
            .appendQueryParameter("api_key", API_KEY)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .appendQueryParameter("extras", "url_s")
            .build();

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


    //添加方法用于下载和搜索
    public List<GalleryItem> fetchRecentPhotos() {
        String url = buildUrl(FETCH_RECENTS_METHOD, null);
        return downloadGalleryItems(url);
    }

    public List<GalleryItem> searchPhotos(String query) {
        String url = buildUrl(SEARCH_METHOD, query);
        return downloadGalleryItems(url);
    }



    //public List<GalleryItem> fetchItems(){}
    private List<GalleryItem> downloadGalleryItems(String url) {
        List<GalleryItem> items = new ArrayList<>();

        try {
            /*
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();
                    */
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);

            /*
            json.org API提供有对应JSON数据的Java对象， 如JSONObject 和JSONArray 。使用
JSONObject(String)构造函数，可以很方便地把JSON数据解析进相应的Java对象。更新
fetchItems()方法执行解析任务
JSONObject构造方法解析传入的Flickr JSON数据后，会生成与原始JSON数据对应的对象树
             */
            //parse json objects解析json数据
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);

        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
        /*
        这里使用Uri.Builder构建了完整的Flickr API请求URL。便利类Uri.Builder可创建正确转
义的参数化URL。Uri.Builder.appendQueryParameter(String,String)可自动转义查询字
符串。
注意，我们还添加了method、api_key、format和nojsoncallback参数值。另外还指定了
一个值为url_s的extras参数。这个参数值告诉Flickr：如有小尺寸图片，也一并返回其URL。
         */
        return items;
    }

    private String buildUrl(String method, String query) {
        Uri.Builder uriBuidler = ENDPOINT.buildUpon().appendQueryParameter("method", method);

        if (method.equals(SEARCH_METHOD)) {
            uriBuidler.appendQueryParameter("text", query);
        }

        return uriBuidler.build().toString();
    }



    /*
    写一个parseItems(...)方法，取出每张图片的信息，生成一个个GalleryItem对象，再将
它们添加到List中
     */
    private void parseItems(List<GalleryItem> items, JSONObject jsonBody) throws IOException, JSONException {
        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");

        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);

            GalleryItem item = new GalleryItem();
            item.setId(photoJsonObject.getString("id"));
            item.setCaption(photoJsonObject.getString("title"));

            if (!photoJsonObject.has("url_s")){
                continue;
            }
            item.setUrl(photoJsonObject.getString("url_s"));
            item.setOwner(photoJsonObject.getString("owner"));
            items.add(item);
        }
    }



}
