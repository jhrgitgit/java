package com.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ForkJoin1 {

    private final static int max = 3;

    public static void main(String[] args) {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> future = forkJoinPool.submit(new CalculateRecuTask(0, 10));
        try {
            Integer result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    private static class CalculateRecuTask extends RecursiveTask<Integer> {

        private final int start;
        private final int end;

        private CalculateRecuTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= max) {
                return IntStream.rangeClosed(start, end).sum();
            } else {
                int middle = (start + end) / 2;
                CalculateRecuTask leftTask = new CalculateRecuTask(start, middle);
                CalculateRecuTask rightTask = new CalculateRecuTask(middle + 1, end);
                leftTask.fork();
                rightTask.fork();
                return leftTask.join() + rightTask.join();
            }
        }
    }
}
