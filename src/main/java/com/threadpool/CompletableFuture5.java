package com.threadpool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFuture5 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        thenAcceptBoth();

        Thread.currentThread().join();

    }

    /**
     * 并行全部执行
     */
    private static void thenAcceptBoth() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("start supplyAsync");
            sleep(5);
            System.out.println("end supplyAsync");
            return "thenAcceptBoth";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println("start thenAcceptBoth");
            sleep(5);
            System.out.println("end thenAcceptBoth");
            return "100";
        }), (s, i) -> System.out.println(s + "--" + i));
    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
