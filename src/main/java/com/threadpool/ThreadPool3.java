package com.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 模拟访问网络资源，线程池一直活着，无法关闭
 */
public class ThreadPool3 {


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1),
                r -> {
                    Thread t = new Thread(r);
                    //守护线程可以关闭线程池
                    t.setDaemon(true);
                    return t;
                }, new ThreadPoolExecutor.AbortPolicy());

        IntStream.range(0, 10).boxed().forEach(i -> executorService.submit(() -> {
            while (true) {

            }
        }));

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("==================over==================");
    }
}
