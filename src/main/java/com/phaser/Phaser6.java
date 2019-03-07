package com.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 主线程监控子线程，
 */
public class Phaser6 {
    public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(7);

      /*  new Thread(() -> phaser.awaitAdvance(phaser.getPhase())).start();

        TimeUnit.SECONDS.sleep(3);

        System.out.println(phaser.getArrivedParties());*/

        IntStream.rangeClosed(0,5).boxed().map(i -> phaser).forEach(AwaitAdvanceTask::new);

        phaser.awaitAdvance(phaser.getPhase());
        System.out.println("===============================");
    }

    private static class AwaitAdvanceTask extends Thread {

        private final Phaser phaser;

        AwaitAdvanceTask(Phaser phaser) {
            this.phaser = phaser;
            start();
        }

        @Override
        public void run() {
            sl(2);
            phaser.arriveAndAwaitAdvance();
//            phaser.arrive();
            System.out.println(getName() + "finished");
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
