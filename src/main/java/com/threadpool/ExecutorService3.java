package com.threadpool;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExecutorService3 {

    /**
     * 回收 core thread (不能重复使用此线程池)
     */
    private static void testAllowCoreTimeout() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        //回收 core thread
        executor.setKeepAliveTime(2, TimeUnit.SECONDS);
        executor.allowCoreThreadTimeOut(true);

        //删除
        //executor.remove();


    }



    private static void testInvokeAny() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Integer>> collect = IntStream.range(0, 5).boxed().map(i -> (Callable<Integer>) () -> {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
            return i;
        }).collect(Collectors.toList());
        //任意一个执行完就返回，其他的不再执行，阻塞。可配置超时时间
        Integer value = executorService.invokeAny(collect);
        //所有都执行，阻塞。可配置超时时间
        List<Future<Integer>> futures = executorService.invokeAll(collect);
        System.out.println("============== finished ===============");
        System.out.println(value);
    }

}
