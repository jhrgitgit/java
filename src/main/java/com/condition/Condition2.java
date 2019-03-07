package com.condition;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Condition2 {

    private final static ReentrantLock lock = new ReentrantLock();

    private final static Condition proCondition = lock.newCondition();

    private final static Condition conCondition = lock.newCondition();

    private final static LinkedList<Long> pool = new LinkedList<>();

    private static int capacity = 100;


    private static void produce() {
        try {
            lock.lock();
            while (pool.size() >= capacity) {
                proCondition.await();
            }
            long value = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " -P-" + value);
            pool.addLast(value);
            conCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void consume() {
        try {
            lock.lock();
            while (pool.isEmpty()) {
                conCondition.await();
            }
            long value = pool.removeFirst();
            System.out.println(Thread.currentThread().getName() + " -C-" + value);
            proCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    private static void sleep(long sec){
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void beginProduce(int i){
        new Thread(() -> {
            for(;;){
                produce();
                sleep(2);
            }
        },"-P-" + i).start();
    }

    private static void beginConsume(int i){
        new Thread(() -> {
            for(;;){
                consume();
                sleep(3);
            }
        },"-C-" + i).start();
    }


    public static void main(String[] args) throws InterruptedException {
        IntStream.range(0,6).boxed().forEach(Condition2::beginProduce);
        IntStream.range(0,13).boxed().forEach(Condition2::beginConsume);
        for(;;){
            TimeUnit.SECONDS.sleep(5);
            System.out.println(lock.getWaitQueueLength(proCondition));
            System.out.println(lock.getWaitQueueLength(conCondition));
        }

    }


}
