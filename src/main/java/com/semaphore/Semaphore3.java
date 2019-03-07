package com.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Semaphore3 {
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(1);

        Thread t0 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.acquire();
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
                System.out.println("t0 finished");
            }
        });
        t0.start();
        try {
            TimeUnit.MICROSECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.acquire();
//                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
                System.out.println("t1 finished");
            }
        });
        t1.start();
        try {
            TimeUnit.MICROSECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
    }

}
