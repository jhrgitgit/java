package com.threadpool;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExecutorService4 {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        executor.execute(() -> System.out.println("I will be process "));
        executor.getQueue().add(() -> System.out.println("I am added"));
    }
}
