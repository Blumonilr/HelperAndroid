package com.nju.edu.helperandroid.util;

import com.alibaba.fastjson.JSON;
import com.nju.edu.helperandroid.web.SeecoderHelperException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import kotlin.jvm.Synchronized;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetUtil {

    private OkHttpClient client;
    private String host;
    private String token;

    public NetUtil(String host,String token){
        this.token=token;
        this.host=host;
        this.client=new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    public Map<String,Object> sendPostRequest(String api, final Object body) throws SeecoderHelperException{
        try {
            Request.Builder builder = new Request.Builder()
                    .addHeader("Authorization", token);
            builder = builder
                    .url(host + api)
                    .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(body)));
            Request request = builder.build();
            final Call call = client.newCall(request);
            Response response = call.execute();
            return processResponse(response);
        } catch (IOException e) {
            throw SeecoderHelperException.IO_ERROR;
        }
    }

    public Map<String,Object> sendGetRequest(String api) throws SeecoderHelperException{
        try {
            Request.Builder builder = new Request.Builder()
                    .addHeader("Authorization", token);
            builder = builder
                    .url(host + api)
                    .get();
            Request request = builder.build();
            final Call call = client.newCall(request);
            Response response = call.execute();
            return processResponse(response);
        } catch (IOException e) {
            throw SeecoderHelperException.IO_ERROR;
        }
    }

    private Map<String, Object> processResponse(Response response) throws SeecoderHelperException {
        validateResponseStatus(response);
        try {
            String data = response.body().string();
            Map<String,Object> res= new HashMap<>();
            res = JSON.parseObject(data,res.getClass());
            return res;
        } catch (IOException e) {
            throw SeecoderHelperException.IO_ERROR;
        }
    }

    private void validateResponseStatus(Response response) throws SeecoderHelperException {
        if (response.code() == 403) {
            throw SeecoderHelperException.UNAHTORIZATION_ERROR;
        } else if (response.code() == 500) {
            throw SeecoderHelperException.INTERNAL_SERVER_ERROR;
        } else if (response.code() != 200) {
            throw SeecoderHelperException.UNKNOWN_ERROR;
        }
    }
}
