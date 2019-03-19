package com.threadpool;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletableFuture2 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        /*List<Callable<Integer>> tasks = IntStream.range(0, 10).boxed().map(i -> (Callable<Integer>) () -> get()).collect(Collectors.toList());

        executorService.invokeAll(tasks).stream().map(future -> {
            try {
                //阻塞操作，第一阶段get完成后，才能执行第二阶段的display
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).parallel().forEach(CompletableFuture2::display);*/

        //阶段一执行结束后，立刻进入下一阶段
        IntStream.range(0, 10).boxed().forEach(i -> CompletableFuture.supplyAsync(CompletableFuture2::get).thenAccept(CompletableFuture2::display)
                .whenComplete((v, t) -> System.out.println(i + " done ")));

        Thread.currentThread().join();


    }

    private static int display(int data) {
        int value = ThreadLocalRandom.current().nextInt(20);
        try {
            System.out.println(Thread.currentThread().getName() + " display will be sleep " + value);
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " display done " + value);
        return value;
    }

    private static int get() {
        int value = ThreadLocalRandom.current().nextInt(20);
        try {
            System.out.println(Thread.currentThread().getName() + " get will be sleep " + value);
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " get done " + value);
        return value;
    }
}
