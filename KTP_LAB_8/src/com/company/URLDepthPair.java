package com.company;

import java.net.*;

/**
 * Класс представляет пару [URL, depth] для нашего Crawler.
 */
public class URLDepthPair {
    /**
     * Поля представляющие текущий URL и depth.
     */
    private int currentDepthGTR;
    private String currentURLGTR;
    /**
     * Конструктор, который устанавливает входящие данные текущего URL и depth.
     */
    public URLDepthPair(String URL, int depth) {
        currentDepthGTR = depth;
        currentURLGTR = URL;
    }
    /**
     * Метод, который возвращает текущий URL.
     */
    public String getURL() {
        return currentURLGTR;
    }
    /**
     * Метод, который возвращает текущий depth.
     */
    public int getDepth() {
        return currentDepthGTR;
    }
    /**
     * Метод, который возвращает текущие URL и depth в формате String.
     */
    public String toString() {
        String stringDepth = Integer.toString(currentDepthGTR);
        return stringDepth + '\t' + currentURLGTR;
    }
    /**
     * Метод, который возвращает docPath текущего URL.
     */
    public String getDocPath() {
        try {
            URL url = new URL(currentURLGTR);
            return url.getPath();
        }
        catch (MalformedURLException e) {
            System.err.println("MalformedURLException: " + e.getMessage());
            return null;
        }
    }
    /**
     * Метод, который возвращает webHost текущего URL.
     */
    public String getWebHost() {
        try {
            URL url = new URL(currentURLGTR);
            return url.getHost();
        }
        catch (MalformedURLException e) {
            System.err.println("MalformedURLException: " + e.getMessage());
            return null;
        }
    }
}