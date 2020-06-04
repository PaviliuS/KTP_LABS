package com.company;

import java.io.IOException;

public class ScannerApp {
    public static void main(String args[]) throws IOException, InterruptedException {
        URLPool url_pool = new URLPool(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        for (int i = 0; i < Integer.parseInt(args[2]); i++) {
            CrawlerTask search_url = new CrawlerTask(url_pool);
            new Thread(search_url).start();
            System.out.println("Поиск " + i + " запущен");
        }
    }
}