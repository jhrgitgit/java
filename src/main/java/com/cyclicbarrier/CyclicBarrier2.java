package com.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrier2 {
    public static void main(String[] args) throws InterruptedException {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    cyclicBarrier.await();
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
//                    TimeUnit.SECONDS.sleep(2);
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //reset == initial=finished
        TimeUnit.SECONDS.sleep(4);
        System.out.println(cyclicBarrier.getNumberWaiting());
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.isBroken());
        TimeUnit.SECONDS.sleep(2);
        cyclicBarrier.reset();
        System.out.println(cyclicBarrier.getNumberWaiting());
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.isBroken());
        /**
         * CountDownLatch 和 CyclicBarrier区别
         * 1 CountDownLatch不能reset，CyclicBarrier区别可以循环使用
         * 2 工作线程之间互不关心，工作线程必须等到同一个共同的点才去执行某个动作
         */

    }
}
