package com.company;

import java.util.LinkedList;

public class URLPool {
    private LinkedList<URLDepthPair> Processed_se = new LinkedList<URLDepthPair>();
    private LinkedList<URLDepthPair> NotProcessed_se = new LinkedList<URLDepthPair>();
    private int Depth_se;
    private int Waiting_se;
    private int Threads_se;

    public URLPool(String url, int depth, int threads) {
        NotProcessed_se.add(new URLDepthPair(url, depth));
        Depth_se = depth;
        Threads_se = threads;
    }

    public synchronized URLDepthPair get() throws InterruptedException {
        if (isEmpty()) {
            Waiting_se++;
            if (Waiting_se == Threads_se) {
                getSites();
                System.exit(0);
            }
            wait();
        }
        return NotProcessed_se.removeFirst();
    }

    public synchronized void addNotProcessed(URLDepthPair pair) {
        NotProcessed_se.add(pair);
        if (Waiting_se > 0) {
            Waiting_se--;
            notify();
        }
    }

    private boolean isEmpty() {
        if (NotProcessed_se.size() == 0) {
            return true;
        }
        return false;
    }

    public void getSites() {
        System.out.println("Глубина поиска: " + Depth_se);
        for (int i = 0; i < Processed_se.size(); i++) {
            System.out.println(Depth_se - Processed_se.get(i).getDepth() + " " +  Processed_se.get(i).getURL());
        }
        System.out.println("Посещённые ссылки: " + Processed_se.size());
    }

    public void addProcessed(URLDepthPair pair) {
        Processed_se.add(pair);
    }

    public LinkedList<URLDepthPair> getProcessed() {
        return Processed_se;
    }

    public LinkedList<URLDepthPair> getNotProcessed() {
        return NotProcessed_se;
    }
}