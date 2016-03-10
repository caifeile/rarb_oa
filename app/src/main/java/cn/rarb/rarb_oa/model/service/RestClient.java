package cn.rarb.rarb_oa.model.service;

import okhttp3.OkHttpClient;

/**
 * Created by cfl on 2016/2/26.
 */
public class RestClient {
    private static final String BASE_URL = "http://gank.avosapps.com/api/data/";
    private static RestClient instance = new RestClient();
    private static ApiService api;

    private RestClient(){
        OkHttpClient okHttpClient = new OkHttpClient();
    }
}
