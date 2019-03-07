package com.reentrant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

public class ReadWriteLock1 {

    private final static ReentrantLock lock = new ReentrantLock(true);

    private final static List<Long> data = new ArrayList<>();

    public static void write() {
        try {
            lock.lock();
            data.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void read() {
        try {
            lock.lock();
            data.forEach(System.out::println);
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + "==================");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(ReadWriteLock1::read);
        thread1.start();

        TimeUnit.SECONDS.sleep(5);

        Thread thread2 = new Thread(ReadWriteLock1::read);
        thread2.start();
    }


}
