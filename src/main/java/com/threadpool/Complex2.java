package com.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * future的问题解决方案，执行结束后立即返回，无需等待
 */
public class Complex2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(5);

        List<Runnable> tasks = IntStream.range(0, 5).boxed().map(Complex2::toTask).collect(Collectors.toList());

        final CompletionService<Object> completionService = new ExecutorCompletionService<Object>(service);
//        tasks.forEach(r -> futureList.add(service.submit(r)));
        tasks.forEach(r -> completionService.submit(Executors.callable(r)));

        Future<?> future;
        while ((future = completionService.take()) != null) {
            System.out.println(future.get());
        }


    }


    private static Runnable toTask(int i) {
        return () -> {
            try {
                System.out.printf("The task [%d] will be executed.\n", i);
                TimeUnit.SECONDS.sleep(i * 5 + 10);
                System.out.printf("The task [%d] will be done.\n", i);
            } catch (InterruptedException e) {
                System.out.printf("The task [%d] will be exception.\n", i);
            }
        };
    }


}
