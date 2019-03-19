package com.threadpool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CompletableFuture4 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "hello");
        //同步
        /*CompletableFuture<String> done = future.whenComplete((v, t) -> System.out.println("done"));
        System.out.println(future.get());
        System.out.println(done.get());*/
        //异步执行(使用返回的新future去调用)
        /*future.whenCompleteAsync((v, t) -> {
            try {
                System.out.println("=================");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("=========over========");
        });
        System.out.println(future.get());*/
        //同步
        CompletableFuture<Integer> integerCompletableFuture = future.thenApply(String::length);
        System.out.println(future.get());
        Thread.currentThread().join();

    }


}
