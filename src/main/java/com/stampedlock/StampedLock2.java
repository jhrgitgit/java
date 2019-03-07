package com.stampedlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

/**
 * 乐观读
 */
public class StampedLock2 {

    private final static StampedLock lock = new StampedLock();

    private final static List<Long> data = new ArrayList<>();

    private static void read() {
        long stamped = lock.tryOptimisticRead();
        if(lock.validate(stamped)){
            try {
                stamped = lock.readLock();
                System.out.println(stamped + "=================");
                Optional.of(data.stream().map(String::valueOf).collect(Collectors.joining("#", "R-", ""))).ifPresent(System.out::println);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlockRead(stamped);
            }
        }
    }

    private static void write() {
        long stamped = -1;
        try {
            stamped = lock.writeLock();
            data.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(3);
            System.out.println("写线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockWrite(stamped);
        }
    }

    public static void main(String[] args) {
        final ExecutorService executor = Executors.newFixedThreadPool(10);
        Runnable readTask = () -> {
            for (; ; ) {
                read();
            }
        };
        Runnable writeTask = () -> {
            for (; ; ) {
                write();
            }
        };
        for (int i = 0; i < 9; i++) {
            executor.submit(readTask);
        }
        executor.submit(writeTask);

    }
}
