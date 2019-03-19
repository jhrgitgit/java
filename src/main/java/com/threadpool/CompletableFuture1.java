package com.threadpool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFuture1 {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((v, t) -> System.out.println("done")); //回调通知返回结果

        System.out.println("===I am not blocked=====================");
        Thread.currentThread().join();
    }
}
