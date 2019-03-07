package com.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatch3 {
    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        final Thread mainThread = Thread.currentThread();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                mainThread.interrupt();
                latch.countDown();
            }
        }).start();

        latch.await(1000, TimeUnit.MILLISECONDS);
        System.out.println("==============");
    }
}
