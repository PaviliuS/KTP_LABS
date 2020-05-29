package com.company;
import java.util.*;

/**
 * CrawlerTask реализует управляемый интерфейс. Каждый экземпляр имеет
 * ссылку на экземпляры класса URLPool. Получает пару URL depth из списка,
 * извлекает веб-страницу, получает все URLs со страницы и добавляет новую
 * пару URLDepth в URL список для каждого найденного URL.
 */
public class CrawlerTask implements Runnable {

    /** Поле для заданного depthPair. */
    public URLDepthPair depthPairGTR;

    /** Поле для списка URL. */
    public URLPool myPoolGTR;

    /** Конструктор для установки переменной URL списка. */
    public CrawlerTask(URLPool pool) {
        myPoolGTR = pool;
    }

    /** Метод для запуска задач CrawlerTask. */
    public void run() {

        /** Получает следующий depthPair из списка. */
        depthPairGTR = myPoolGTR.get();

        int myDepth = depthPairGTR.getDepth();

        /** Получает все ссылки с сайта и хранит их в новом LinkedList. */
        LinkedList<String> linksList = new LinkedList<String>();
        linksList = Crawler.getAllLinks(depthPairGTR);

        for (int i=0;i<linksList.size();i++) {
            String newURL = linksList.get(i);

            /** Создаёт новый depthPair для каждой найденной ссылки и добавляет в список. */
            URLDepthPair newDepthPair = new URLDepthPair(newURL, myDepth + 1);
            myPoolGTR.put(newDepthPair);
        }
    }
}