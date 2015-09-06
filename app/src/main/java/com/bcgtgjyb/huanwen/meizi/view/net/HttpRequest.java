package com.bcgtgjyb.huanwen.meizi.view.net;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by huanwen on 2015/9/5.
 */
public class HttpRequest {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String TAG="HttpRequest";
    OkHttpClient client;

    public HttpRequest() {
        client=new OkHttpClient();
    }

    public String httpGet(String url) throws IOException {
        return httpGetResponse(url).body().string();
    }

    public Response httpGetResponse(String url) throws IOException{
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public Response httpPostResponse(String url,String json) throws IOException{
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public String httpPost(String url,String json) throws IOException{
        return httpPostResponse(url,json).body().string();
    }

}
