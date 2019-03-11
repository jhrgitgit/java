package com.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ExecutorService1 {

    public static void main(String[] args) throws InterruptedException {
//        isShutDown();
        exeTask();
    }

    private static void isShutDown() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(executorService.isShutdown());
        executorService.shutdown();//shutdown后不能再执行任务
        System.out.println(executorService.isShutdown());
    }

    private static void isTerminated() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();//shutdown后不能再执行任务
        System.out.println("isShutdown: " + executorService.isShutdown());
        System.out.println("isTerminated: " + executorService.isTerminated());
    }

    private static void exeError() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10, new MyThreadFactory());
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        IntStream.range(0, 10).boxed().forEach(i -> executorService.execute(() -> System.out.println(1 / 0)));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("================================");
    }

    private static void exeTask() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10, new MyThreadFactory());
        IntStream.range(0, 10).boxed().forEach(i -> executorService.execute(new MyTask(i) {
            @Override
            protected void error(Throwable cause) {
                System.out.println("the no: " + i + " failed ");
            }

            @Override
            protected void done() {
                System.out.println("the no: " + i + " done ");
            }

            @Override
            protected void doExecute() {
                if (i % 3 == 0) {
                    int tem = i / 0;
                }
            }

            @Override
            protected void doInit() {

            }
        }));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("================================");
    }

    private abstract static class MyTask implements Runnable {
        protected final int no;

        private MyTask(int no) {
            this.no = no;
        }

        @Override
        public void run() {
            try {
                this.doInit();
                this.doExecute();
                this.done();
            } catch (Throwable cause) {
                this.error(cause);
            }
        }

        protected abstract void error(Throwable cause);

        protected abstract void done();

        protected abstract void doExecute();

        protected abstract void doInit();
    }


    private static class MyThreadFactory implements ThreadFactory {

        private final static AtomicInteger seq = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("My-thread-" + seq.getAndIncrement());
            thread.setUncaughtExceptionHandler((t, cause) -> {
                System.out.println("the thread " + t.getName() + " execute failed ");
                cause.printStackTrace();
                System.out.println("===============================================");
            });
            return thread;
        }
    }


}
