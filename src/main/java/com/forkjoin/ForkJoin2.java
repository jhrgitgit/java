package com.forkjoin;

import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ForkJoin2 {

    private final static int max = 3;

    private final static AtomicInteger sum = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(new CalculateRecuAction(0, 10));
        forkJoinPool.awaitTermination(10,TimeUnit.SECONDS);
        Optional.of(sum).ifPresent(System.out::println);
    }


    private static class CalculateRecuAction extends RecursiveAction {

        private final int start;
        private final int end;

        private CalculateRecuAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= max) {
                sum.addAndGet(IntStream.rangeClosed(start, end).sum());
            } else {
                int middle = (start + end) / 2;
                CalculateRecuAction leftTask = new CalculateRecuAction(start, middle);
                CalculateRecuAction rightTask = new CalculateRecuAction(middle + 1, end);
                leftTask.fork();
                rightTask.fork();
            }
        }
    }
}
