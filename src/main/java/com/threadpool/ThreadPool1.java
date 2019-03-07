package com.threadpool;

import java.util.concurrent.*;

public class ThreadPool1 {
    public static ExecutorService build() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1),
                r -> {
                    Thread t = new Thread(r);
                    return t;
                }, new ThreadPoolExecutor.AbortPolicy());
        System.out.println("the pool created");

        executorService.execute(() -> sleepSeconds(100));
        executorService.execute(() -> sleepSeconds(10));
        executorService.execute(() -> sleepSeconds(10));
        return executorService;
    }


    private static void sleepSeconds(long seconds){
        try {
            System.out.println("* " +Thread.currentThread().getName() + " *");
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) build();

        int activeCount = -1;
        int queueSize = -1;

        while (true) {
            if (activeCount != threadPoolExecutor.getActiveCount() || queueSize != threadPoolExecutor.getQueue().size()) {
                System.out.println("ActiveCount: " + threadPoolExecutor.getActiveCount());
                System.out.println("CorePoolSize: " + threadPoolExecutor.getCorePoolSize());
                System.out.println("queue size: " + threadPoolExecutor.getQueue().size());
                System.out.println("max pool size: "+ threadPoolExecutor.getMaximumPoolSize());
                activeCount = threadPoolExecutor.getActiveCount();
                queueSize = threadPoolExecutor.getQueue().size();
                System.out.println("================================================================================");
            }
        }
    }
}
