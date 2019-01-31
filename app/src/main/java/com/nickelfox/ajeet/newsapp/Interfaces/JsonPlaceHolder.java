package com.nickelfox.ajeet.newsapp.Interfaces;

import com.nickelfox.ajeet.newsapp.Activities.SourceFilterActivity;
import com.nickelfox.ajeet.newsapp.Models.Articles;

import com.nickelfox.ajeet.newsapp.Models.EverthingsData;
import com.nickelfox.ajeet.newsapp.Models.SourceData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolder {
    @GET("everything?q=bitcoin&sortBy=publishedAt&apiKey=c915eb9a21964da9903c0669a199dd62")
    Call<Articles>getEveryThing();
    @GET("top-headlines?country=us&category=business&apiKey=c915eb9a21964da9903c0669a199dd62")
    Call<Articles>getHeadLine();
    @GET("sources?apiKey=c915eb9a21964da9903c0669a199dd62")
    Call<SourceData>getAllSource();

    @GET("sources?")
    Call<SourceData>getFilterSource(@Query("language")String lang ,
                                    @Query("country")String cont,
                                    @Query("apiKey")String api);
    @GET("sources?")
    Call<SourceData>getFilterSourceLang(@Query("language")String lang ,
                                        @Query("apiKey")String api);
    @GET("sources?")
    Call<SourceData>getFilterSourceCountry(@Query("country")String cont,
                                           @Query("apiKey")String api);
}
