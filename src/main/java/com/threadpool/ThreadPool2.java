package com.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 关闭
 */
public class ThreadPool2 {


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1),
                r -> {
                    Thread t = new Thread(r);
                    return t;
                }, new ThreadPoolExecutor.AbortPolicy());

        IntStream.range(0, 20).boxed().forEach(i -> executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println(Thread.currentThread().getName() + "[" + i + "] finished ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        executorService.shutdown();
        //返回被打断的线程列表
//        executorService.shutdownNow();
        //串行化
        executorService.awaitTermination(1,TimeUnit.HOURS);
        System.out.println("==================over==================");
    }
}
