package com.threadpool;

import java.util.concurrent.*;

public class Future1 {

    private static void testGet() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        Future<Integer> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        System.out.println("========I will be print quickly ========================");

        //打断主线程,get方法
        Thread callerThread = Thread.currentThread();

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            callerThread.interrupt();
        }).start();

        Integer result = future.get();

        System.out.println(result);
    }


}
