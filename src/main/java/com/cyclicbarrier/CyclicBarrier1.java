package com.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrier1 {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("all of finished");
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("T1 begin ");
                    cyclicBarrier.await();
                    System.out.println("T1 finished ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("T2 begin ");
                    cyclicBarrier.await();
                    System.out.println("T2 finished ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        while (true) {
            System.out.println(cyclicBarrier.getNumberWaiting());
            System.out.println(cyclicBarrier.getParties());
            System.out.println(cyclicBarrier.isBroken());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
