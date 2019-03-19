package com.threadpool;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * CompletionService关注的是完成的任务
 */
public class Complex3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

       /* ExecutorService service = Executors.newSingleThreadExecutor();

        List<Runnable> tasks = IntStream.range(0, 5).boxed().map(Complex3::toTask).collect(Collectors.toList());

        final CompletionService<Object> completionService = new ExecutorCompletionService<Object>(service);
        tasks.forEach(r -> completionService.submit(Executors.callable(r)));

        TimeUnit.SECONDS.sleep(11);
        //列表中存在未完成的任务，不存在中断的任务
        List<Runnable> runnables = service.shutdownNow();
        System.out.println(runnables.size());*/


        ExecutorService service = Executors.newSingleThreadExecutor();

        List<Callable<Integer>> tasks = IntStream.range(0, 5).boxed().map(MyTask::new).collect(Collectors.toList());

        final CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(service);

        tasks.forEach(completionService::submit);
        TimeUnit.SECONDS.sleep(11);
        service.shutdownNow();

        //返回所有未完成的任务
        tasks.stream().filter(callable -> !((MyTask)callable).isSuccess()).forEach(System.out::println);


    }

    private static class MyTask<Integer> implements Callable<Integer> {

        private final Integer value;

        private boolean success = false;

        MyTask(Integer value) {
            this.value = value;
        }

        @Override
        public Integer call() throws Exception {
            System.out.printf("The task [%d] will be executed.\n", value);
            TimeUnit.SECONDS.sleep( 5 + 10);
            System.out.printf("The task [%d] will be done.\n", value);
            success = true;
            return value;
        }

        public boolean isSuccess() {
            return success;
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
