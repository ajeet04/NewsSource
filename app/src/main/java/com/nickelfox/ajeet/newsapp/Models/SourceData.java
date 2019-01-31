package com.nickelfox.ajeet.newsapp.Models;

import java.io.Serializable;

public class SourceData implements Serializable {
    private AllSource[] sources;

    public AllSource[] getSource() {
        return sources;
    }
}
