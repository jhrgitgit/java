package com.reentrant;


import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReentrantLock1 {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void testUnInterruptibly() {
        try {
            //可以被打断
            lock.lockInterruptibly();
            Optional.of(Thread.currentThread().getName() + " working").ifPresent(System.out::println);
            while (true) {

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void testTryLock() {

        if (lock.tryLock()) {
            try {
                Optional.of(Thread.currentThread().getName() + " working").ifPresent(System.out::println);
                while (true) {

                }
            } finally {
                lock.unlock();
            }
        } else {
            Optional.of(Thread.currentThread().getName() + " not get lock").ifPresent(System.out::println);
        }
    }

    public static void needLock() {
        try {
            lock.lock();
            Optional.of(Thread.currentThread().getName() + " working").ifPresent(System.out::println);
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /*IntStream.range(0,2).forEach(i -> new Thread(){
            @Override
            public void run() {
                needLock();
            }
        }.start());*/

       /* Thread t1 = new Thread(() -> testUnInterruptibly());
        t1.start();

        TimeUnit.SECONDS.sleep(1);

        Thread t2 = new Thread(() -> testUnInterruptibly());
        t2.start();

        TimeUnit.SECONDS.sleep(1);

        t2.interrupt();
        System.out.println("打断之后");*/
        Thread t1 = new Thread(() -> testTryLock());
        t1.start();

        TimeUnit.SECONDS.sleep(1);

        Thread t2 = new Thread(() -> testTryLock());
        t2.start();
    }

}
