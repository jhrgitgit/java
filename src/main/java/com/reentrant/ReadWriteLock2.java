package com.reentrant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock2 {

    private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final static Lock readLock = lock.readLock();
    private final static Lock writeLock = lock.writeLock();

    private final static List<Long> data = new ArrayList<>();

    public static void write() {
        try {
            writeLock.lock();
            data.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    public static void read() {
        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + "进入 read ==================");
            data.forEach(System.out::println);
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + "==================");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(ReadWriteLock2::read);
        thread1.start();

        Thread thread2 = new Thread(ReadWriteLock2::read);
        thread2.start();
    }


}
