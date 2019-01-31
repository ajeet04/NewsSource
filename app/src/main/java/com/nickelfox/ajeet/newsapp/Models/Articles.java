package com.nickelfox.ajeet.newsapp.Models;

import java.io.Serializable;

public class Articles implements Serializable {
    private EverthingsData[] articles;

    public EverthingsData[] getArticles() {
        return articles;
    }
}
