package com.nickelfox.ajeet.newsapp.Utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
   private static String  BASE_URL="https://newsapi.org/v2/";
    public static RetrofitClient instance;
    private RetrofitClient(){

    }

    public static Retrofit getReference() {
        Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
return retrofit;
    }

    public  RetrofitClient getInstance(){
        if(instance==null){
            instance=new RetrofitClient();
        }
        return instance;
    }




}
