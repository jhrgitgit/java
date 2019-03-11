package com.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * future的问题，4一直阻塞，321拿不到结果
 */
public class Complex {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(5);

        List<Runnable> tasks = IntStream.range(0, 5).boxed().map(Complex::toTask).collect(Collectors.toList());

        List<Future<?>> futureList = new ArrayList<>();
        tasks.forEach(r -> futureList.add(service.submit(r)));

        futureList.get(4).get();
        System.out.println("=======4========");
        futureList.get(3).get();
        System.out.println("=======3========");
        futureList.get(2).get();
        System.out.println("=======2========");
        futureList.get(1).get();
        System.out.println("=======1========");
        futureList.get(0).get();
        System.out.println("=======0========");
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
