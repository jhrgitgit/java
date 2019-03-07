package com.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Semaphore2 {
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(1);

        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + " is running ==");
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName() + " get ==");
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                    }
                    System.out.println(Thread.currentThread().getName() + " release ==");
                }
            }).start();
        }
        while (true){
            System.out.println("AP -> " + semaphore.availablePermits());
            System.out.println("QL -> " + semaphore.getQueueLength());
            System.out.println("=================================");
        }
    }

}
