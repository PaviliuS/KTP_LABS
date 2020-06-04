package com.company;

import java.io.IOException;

public class ScannerApp {
    public static void main(String args[]) throws IOException {
        /** Аргументы программы: [0] - ссылка, [1] - глубина поиска */
        Crawler search_url = new Crawler(args[0], Integer.parseInt(args[1]));
        search_url.Scan();
        System.out.println("Глубина поиска: " + Integer.parseInt(args[1]));
        search_url.getSites();
    }
}