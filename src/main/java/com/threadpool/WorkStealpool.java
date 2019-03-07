package com.threadpool;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 使用cpu核心线程数量执行任务
 */
public class WorkStealpool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newWorkStealingPool();


        List<Callable<String>> collect = IntStream.range(0, 20).boxed().map(i ->
                (Callable<String>) () -> {
                    System.out.println(Thread.currentThread().getName() + " 在运行 ");
                    sleepSeconds(2);
                    return "Task-" + i;
                }
        ).collect(Collectors.toList());

        executorService.invokeAll(collect).stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).forEach(System.out::println);
    }

    private static void sleepSeconds(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
