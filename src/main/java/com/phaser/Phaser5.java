package com.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 主线程只等待第一阶段完成，
 */
public class Phaser5 {
    public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(5);
        for (int i = 0; i < 4; i++) {
            new ArriveTask(i, phaser).start();
        }
        phaser.arriveAndAwaitAdvance();

        System.out.println("the parse 1 done");
    }

    static class ArriveTask extends Thread {

        private final Phaser phaser;

        ArriveTask(int no, Phaser phaser) {
            super(String.valueOf(no));
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(getName() + " I am start1 phaser = " + phaser.getPhase());
            sl(2);
            System.out.println(getName() + " I am end1 phaser = " + phaser.getPhase());
            phaser.arrive();

            sl(2);

            System.out.println(getName() + "do other thing");

        }
    }

    private static void sl(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
