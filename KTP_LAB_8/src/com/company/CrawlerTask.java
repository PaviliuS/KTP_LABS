package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CrawlerTask implements Runnable {
    final static int AnyDepth = 0;
    private URLPool Pool_se;

    /** Нет "/" для поддержки https */
    private String Prefix_se = "http";

    @Override
    public void run() {
        try {
            Scan();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public CrawlerTask(URLPool pool) {
        Pool_se = pool;
    }

    private  void  Scan() throws IOException, InterruptedException {
        while (true) {
            Process(Pool_se.get());

        }
    }

    private void Process(URLDepthPair pair) throws IOException{
        /** Установка соединения и перенаправление */
        URL url = new URL(pair.getURL());
        URLConnection connection = url.openConnection();

        String redirect = connection.getHeaderField("Местоположение");
        if (redirect != null) {
            connection = new URL(redirect).openConnection();
        }
        Pool_se.addProcessed(pair);

        if (pair.getDepth() == 0) {
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input;
        while ((input = reader.readLine()) != null) {
            while (input.contains("a href=\"" + Prefix_se)) {
                input = input.substring(input.indexOf("a href=\"" + Prefix_se) + 8);
                String link = input.substring(0, input.indexOf('\"'));
                if(link.contains(" ")){
                    link = link.replace(" ", "%20");
                }
                /** Не обрабатывает посещение одинаковых ссылок */
                if (Pool_se.getNotProcessed().contains(new URLDepthPair(link, AnyDepth)) ||
                        Pool_se.getProcessed().contains(new URLDepthPair(link, AnyDepth))) {
                    continue;
                }
                Pool_se.addNotProcessed(new URLDepthPair(link, pair.getDepth() - 1));
            }
        }
        reader.close();
    }
}