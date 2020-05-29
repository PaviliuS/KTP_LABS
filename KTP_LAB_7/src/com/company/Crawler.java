package com.company;
import java.net.*;
import java.util.*;
import java.io.*;

/**
 * Этот класс представляет главную функциональность нашего приложения.
 * Приложение имеет метод getAllLinks для хранения всех ссылок на данной веб-странице
 * в дополнение к основному методу, который продолжает отслеживать важные переменные.
 */
public class Crawler {
    /**
     * Основной метод crawler. Программа должна приниматть строку,
     * которая представляет URL, с которого начинается просмотр и положительное
     * целое число, представляющее максимальную depth поиска. Хранит URL как строку с
     * глубиной как URLDepthPair. Отслеживает обработанные ссылки, ожидающие ссылки,
     * просмотренные ссылки и глубину. Выводит список всех обработанных ссылок с их глубиной.
     * Перебирает pendingURLs, чтобы получить все ссылки и добавить их в обработанные URLs и
     * просмотренные URLs.
     */
    public static void main(String[] args) {

        int depthGTR = 0;

        if (args.length != 2) {
            System.out.println("usage: java Crawler <URL> <depth>");
            System.exit(1);
        }
        else {
            try {
                depthGTR = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException nfe) {
                System.out.println("usage: java Crawler <URL> <depth>");
                System.exit(1);
            }
        }

        LinkedList<URLDepthPair> pendingURLsGTR = new LinkedList<URLDepthPair>();
        LinkedList<URLDepthPair> processedURLsGTR = new LinkedList<URLDepthPair>();
        URLDepthPair currentDepthPairGTR = new URLDepthPair(args[0], 0);
        pendingURLsGTR.add(currentDepthPairGTR);
        ArrayList<String> seenURLsGTR = new ArrayList<String>();
        seenURLsGTR.add(currentDepthPairGTR.getURL());

        while (pendingURLsGTR.size() != 0) {

            URLDepthPair depthPairGTR = pendingURLsGTR.pop();
            processedURLsGTR.add(depthPairGTR);
            int myDepthGTR = depthPairGTR.getDepth();

            LinkedList<String> linksList = new LinkedList<String>();
            linksList = Crawler.getAllLinks(depthPairGTR);

            if (myDepthGTR < depthGTR) {
                for (int i=0;i<linksList.size();i++) {
                    String newURL = linksList.get(i);
                    if (seenURLsGTR.contains(newURL)) {
                        continue;
                    }
                    else {
                        URLDepthPair newDepthPair = new URLDepthPair(newURL, myDepthGTR + 1);
                        pendingURLsGTR.add(newDepthPair);
                        seenURLsGTR.add(newURL);
                    }
                }
            }
        }

        Iterator<URLDepthPair> iterGTR = processedURLsGTR.iterator();
        while (iterGTR.hasNext()) {
            System.out.println(iterGTR.next());
        }
    }
    /**
     * Метод, который берёт URLDepthPair и возвращает LinkedList в формате String.
     * Подключается к сайту в паре URLDepth, находит все ссылки на сайте и добавляет
     * их в новый LinkedList, который потом вовращается.
     */
    private static LinkedList<String> getAllLinks(URLDepthPair myDepthPair) {

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