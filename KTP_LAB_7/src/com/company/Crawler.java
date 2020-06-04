package com.company;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class Crawler {
    final static int AnyDepth = 0;
    private LinkedList<URLDepthPair> Processed_se = new LinkedList<URLDepthPair>();
    private LinkedList<URLDepthPair> NotProcessed_se = new LinkedList<URLDepthPair>();

    private int Depth_se;
    private String StartHost_se;

    /** Нет "/" для поддержки https */
    private String Prefix_se = "http";

    public Crawler(String host, int depth) {
        StartHost_se = host;
        Depth_se = depth;
        NotProcessed_se.add(new URLDepthPair(StartHost_se, Depth_se));
    }

    public void Scan() throws IOException {

        while (NotProcessed_se.size() > 0) {
            Process(NotProcessed_se.removeFirst());
        }
    }

    public void Process(URLDepthPair pair) throws IOException{
        /** Установка соединения и перенаправление */
        URL url = new URL(pair.getURL());
        URLConnection url_connection = url.openConnection();
        String redirect = url_connection.getHeaderField("Местоположение");

        if (redirect != null) {
            url_connection = new URL(redirect).openConnection();
        }
        Processed_se.add(pair);

        if (pair.getDepth() == 0) {
            return;
        }

        /** Чтение ссылки */
        BufferedReader reader = new BufferedReader(new InputStreamReader(url_connection.getInputStream()));
        String input;
        while ((input = reader.readLine()) != null) {
            while (input.contains("a href=\"" + Prefix_se)) {
                input = input.substring(input.indexOf("a href=\"" + Prefix_se) + 8);
                String link = input.substring(0, input.indexOf('\"'));
                if(link.contains(" ")) {
                    link = link.replace(" ", "%20");
                }
                /** Не обрабатывает посещение одинаковых ссылок */
                if (NotProcessed_se.contains(new URLDepthPair(link, AnyDepth)) ||
                        Processed_se.contains(new URLDepthPair(link, AnyDepth))) {
                    continue;
                }
                NotProcessed_se.add(new URLDepthPair(link, pair.getDepth() - 1));
            }
        }
        reader.close();
    }

    public void getSites() {
        /** Вывод ссылок */
        for (URLDepthPair elem : Processed_se){
            System.out.println(elem.getURL());
        }
        System.out.println("Посещённые ссылки: " + Processed_se.size());
    }
}