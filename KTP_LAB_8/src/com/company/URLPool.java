package com.company;

import java.util.*;

/**
 * Класс списка URL, который хранит список URL адресов, которые найдены
 * с заданной depth. Хранится как экземпляр URLDepthPair.
 */
public class URLPool {

    /** Связанный список, для представления, ожидающих URL адресов. */
    private LinkedList<URLDepthPair> pendingURLsGTR;

    /** Связяннный список, для представления обработанных URL адресов. */
    public LinkedList<URLDepthPair> processedURLsGTR;

    /** Класс ArrayList для представления просмотренных URL адресов. */
    private ArrayList<String> seenURLsGTR = new ArrayList<String>();

    /** int, для отслеживания количества ожидающих потоков. */
    public int waitingThreadsGTR;

    /**
     * Конструктор для инициализации ожидающих потоков, обработанных URL адресов и
     * ожидающих URL адресов.
     */
    public URLPool() {
        waitingThreadsGTR = 0;
        pendingURLsGTR = new LinkedList<URLDepthPair>();
        processedURLsGTR = new LinkedList<URLDepthPair>();
    }

    /** Синхронизированный метод, для получения количества ожидающих потоков. */
    public synchronized int getWaitThreads() {
        return waitingThreadsGTR;
    }

    /** Синхронизированный метод, для возврата размера списка. */
    public synchronized int size() {
        return pendingURLsGTR.size();
    }

    /** Синхронизированный метод, для добавления depthPair в список. */
    public synchronized boolean put(URLDepthPair depthPair) {

        /** Переменная для отслеживания, был ли добавлен depthPair. */
        boolean added = false;

        /** Если depth меньше чем max depth, добавляет depthPair в список. */
        if (depthPair.getDepth() < depthPair.getDepth()) {
            pendingURLsGTR.addLast(depthPair);
            added = true;

            /** Уменьшает количество ожидающих потоков. */
            waitingThreadsGTR--;
            this.notify();
        }
        /**
         * Если depth не меньше чем max depth, просто добавляет depthPair
         * в просмотренный список.
         */
        else {
            seenURLsGTR.add(depthPair.getURL());
        }
        return added;
    }

    /** Синхронизированный метод, для получения следующей depthPair из списка. */
    public synchronized URLDepthPair get() {

        /** Устанавливает depthPair = null. */
        URLDepthPair myDepthPair = null;

        /** Пока список пуст, ожидает. */
        if (pendingURLsGTR.size() == 0) {
            waitingThreadsGTR++;
            try {
                this.wait();
            }
            catch (InterruptedException e) {
                System.err.println("MalformedURLException: " + e.getMessage());
                return null;
            }
        }

        /**
         * Удаляет первый depthPair, добавляет просмотренные и обработанные
         * URL адреса и возвращает их.
         */
        myDepthPair = pendingURLsGTR.removeFirst();
        seenURLsGTR.add(myDepthPair.getURL());
        processedURLsGTR.add(myDepthPair);
        return myDepthPair;
    }

    /** Синхронизированный метод для получения, просмотренных URL адресов. */
    public synchronized ArrayList<String> getSeenList() {
        return seenURLsGTR;
    }
}