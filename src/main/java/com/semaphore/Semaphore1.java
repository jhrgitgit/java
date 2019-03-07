package com.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Semaphore1 {
    public static void main(String[] args) {
        final SemaphoreLock lock = new SemaphoreLock();

        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + " is running ==");
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + " get ==");
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                    System.out.println(Thread.currentThread().getName() + " release ==");
                }
            }).start();
        }
    }

    static class SemaphoreLock {
        private Semaphore semaphore = new Semaphore(1);

        public void lock() throws InterruptedException {
            semaphore.acquire();
        }

        public void unlock() {
            semaphore.release();
        }

    }
}
