package com.company;

import java.util.Objects;

public class URLDepthPair {
    private String Url_se;
    private int Depth_se;

    public URLDepthPair(String host, int depth) {
        Url_se = host;
        Depth_se = depth;
    }

    public String getURL() {
        return Url_se;
    }

    public int getDepth() {
        return Depth_se;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof URLDepthPair) {
            URLDepthPair o = (URLDepthPair)obj;
            return this.Url_se.equals(o.getURL());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }
}