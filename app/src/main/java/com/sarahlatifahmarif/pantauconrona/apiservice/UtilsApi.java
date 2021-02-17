package com.sarahlatifahmarif.pantauconrona.apiservice;

public class UtilsApi {
    public static final String BASE_URL_API = "https://minangtech.com/home/";

    public static BaseApiService getLatestInfo(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
    public static BaseApiService getDaysInfo(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
    public static BaseApiService getDayEachKab(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
    public static BaseApiService getKab(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
