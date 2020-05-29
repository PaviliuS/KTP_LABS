package com.company;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 * Этот класс обрабатывает аргументы командной строки, создаёт экземпляр списка URL,
 * добавляет добавленные URL в список и создаёт некоторое количество задач класса,
 * которые введены вместе с потоками для их выполнения. Тогда, когда процесс поиска
 * завершён, выводит список найденных адресов.
 */
public class Crawler {

    /**
     * Метод, который выполняет задачи Crawler.
     */
    public static void main(String[] args) {

        int depthGTR = 0;
        int numThreadsGTR = 0;

        if (args.length != 3) {
            System.out.println("usage: java Crawler <URL> <depth> <number of crawler threads");
            System.exit(1);
        }
        else {
            try {
                depthGTR = Integer.parseInt(args[1]);
                numThreadsGTR = Integer.parseInt(args[2]);
            }
            catch (NumberFormatException nfe) {
                System.out.println("usage: java Crawler <URL> <depth> <number of crawler threads");
                System.exit(1);
            }
        }

        URLDepthPair currentDepthPairGTR = new URLDepthPair(args[0], 0);

        URLPool pool = new URLPool();
        pool.put(currentDepthPairGTR);

        int totalThreadsGTR = 0;
        int initialActiveGTR = Thread.activeCount();

        while (pool.getWaitThreads() != numThreadsGTR) {
            if (Thread.activeCount() - initialActiveGTR < numThreadsGTR) {
                CrawlerTask crawler = new CrawlerTask(pool);
                new Thread(crawler).start();
            }
            else {
                try {
                    Thread.sleep(100);  // 0.1 second
                }
                catch (InterruptedException ie) {
                    System.out.println("Caught unexpected " +
                            "InterruptedException, ignoring...");
                }
            }
        }

        Iterator<URLDepthPair> iterGTR = pool.processedURLsGTR.iterator();
        while (iterGTR.hasNext()) {
            System.out.println(iterGTR.next());
        }        // Exit.
        System.exit(0);
    }

    /**
     * Метод, который берёт URLDepthPair и возвращает LinkedList в формате String.
     * Подключается к сайту в паре URLDepth, находит все ссылки на сайте и добавляет
     * их в новый LinkedList, который потом вовращается.
     */
    public static LinkedList<String> getAllLinks(URLDepthPair myDepthPair) {

        LinkedList<String> URLsGTR = new LinkedList<String>();
        Socket sockGTR;

        try {
            sockGTR = new Socket(myDepthPair.getWebHost(), 80);
        }
        catch (UnknownHostException e) {
            System.err.println("UnknownHostException: " + e.getMessage());
            return URLsGTR;
        }
        catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
            return URLsGTR;
        }

        try {
            sockGTR.setSoTimeout(3000);
        }
        catch (SocketException exc) {
            System.err.println("SocketException: " + exc.getMessage());
            return URLsGTR;
        }

        String docPath = myDepthPair.getDocPath();
        String webHost = myDepthPair.getWebHost();

        OutputStream outStreamGTR;

        try {
            outStreamGTR = sockGTR.getOutputStream();
        }
        catch (IOException exce) {
            System.err.println("IOException: " + exce.getMessage());
            return URLsGTR;
        }

        PrintWriter myWriter = new PrintWriter(outStreamGTR, true);

        myWriter.println("GET " + docPath + " HTTP/1.1");
        myWriter.println("Host: " + webHost);
        myWriter.println("Connection: close");
        myWriter.println();

        InputStream inStreamGTR;

        try {
            inStreamGTR = sockGTR.getInputStream();
        }
        catch (IOException excep){
            System.err.println("IOException: " + excep.getMessage());
            return URLsGTR;
        }
        InputStreamReader inStreamReader = new InputStreamReader(inStreamGTR);
        BufferedReader BuffReader = new BufferedReader(inStreamReader);

        while (true) {
            String line;
            try {
                line = BuffReader.readLine();
            }
            catch (IOException except) {
                System.err.println("IOException: " + except.getMessage());
                return URLsGTR;
            }

            if (line == null) {
                break;
            }

            int beginIndexGTR = 0;
            int endIndexGTR = 0;
            int indexGTR = 0;

            while (true) {
                /**
                 * Константа для строки, которая указывает на ссылку.
                 */
                String URL_INDICATOR = "a href=\"";

                /**
                 * Константа для строки, указывающая на конец веб-хоста и начало docpath.
                 */
                String END_URL = "\"";

                indexGTR = line.indexOf(URL_INDICATOR, indexGTR);
                if (indexGTR == -1) {
                    break;
                }

                indexGTR += URL_INDICATOR.length();
                beginIndexGTR = indexGTR;

                endIndexGTR = line.indexOf(END_URL, indexGTR);
                indexGTR = endIndexGTR;

                String newLink = line.substring(beginIndexGTR, endIndexGTR);
                URLsGTR.add(newLink);
            }
        }
        return URLsGTR;
    }
}