package com.threadpool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CompletableFuture3 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

//        supplyAsync();
//        Future<?> future = runAsync();
//        future.get();
        Future<Void> future = completedFuture("hello");
        System.out.println(future.isDone());
        Thread.currentThread().join();
    }

    private static Future<Void> completedFuture(String data){
        return CompletableFuture.completedFuture(data).thenAccept(System.out::println);
    }

    private static Future<?> runAsync(){
        return CompletableFuture.runAsync(() -> {
            try {
                System.out.println("Obj ====== start ");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Obj ====== end ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((v,t) -> System.out.println("====over====="));
    }






    /**
     * 并行执行，前后顺序不一定
     */
    private static void supplyAsync(){
        CompletableFuture.supplyAsync(Object::new).thenAcceptAsync(obj -> {
            try {
                System.out.println("Obj ====== start ");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Obj ====== end ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> " hello ").thenAcceptAsync(obj -> {
            try {
                System.out.println("string ====== start ");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("string ====== end ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }),() -> System.out.println(" finished "));
    }

    /**
     * 任意取出一个
     * @return
     */
    private static Future<?> anyof(){
        return CompletableFuture.anyOf(CompletableFuture.runAsync(() -> {
            try {
                System.out.println("1 ====== start ");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("1 ====== end ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((v,t) -> System.out.println("====over=====")),
                CompletableFuture.runAsync(() -> {
            try {
                System.out.println("2 ====== start ");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("2 ====== end ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((v,t) -> System.out.println("====over=====")));
    }

}
