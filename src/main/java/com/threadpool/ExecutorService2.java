package com.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ExecutorService2 {

    /**
     * 回收 core thread (不能重复使用此线程池)
     */
    private static void testAllowCoreTimeout(){
        ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);

        //回收 core thread
        executor.setKeepAliveTime(2,TimeUnit.SECONDS);
        executor.allowCoreThreadTimeOut(true);

        //删除
        //executor.remove();

        IntStream.range(0,5).boxed().forEach(i -> {
            executor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
