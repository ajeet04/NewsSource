package com.nickelfox.ajeet.newsapp.Models;

import java.io.Serializable;

public class EverthingsData  implements Serializable {
    private String author;
    private String title;
    private String description;
    private String urlToImage;
    private String publishedAt;
    private String content;
    private String url;

    private source source;

    public EverthingsData(String author, String title, String description, String urlToImage, String publishedAt, String content, String url, EverthingsData.source source) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
        this.url = url;
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public EverthingsData.source getSource() {
        return source;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSource(EverthingsData.source source) {
        this.source = source;
    }

    private class source {
        private String id;
        private String name;

        public source(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
