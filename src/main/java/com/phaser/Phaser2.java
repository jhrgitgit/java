package com.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 铁人三项
 */
public class Phaser2 {
    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);

        for (int i = 1; i < 6; i++) {
            new Athletes(phaser, i).start();
        }
    }

    static class Athletes extends Thread {
        private final Phaser phaser;
        private final int no;

        Athletes(Phaser phaser, int no) {
            this.phaser = phaser;
            this.no = no;
        }

        @Override
        public void run() {

            try {
                System.out.println(no + " start 1 ");
                TimeUnit.SECONDS.sleep(2);
                System.out.println(no + " end 1 ");
                System.out.println("getPhase1 -> " + phaser.getPhase());
                phaser.arriveAndAwaitAdvance();

                System.out.println(no + " start 2 ");
                TimeUnit.SECONDS.sleep(2);
                System.out.println(no + " end 2 ");
                System.out.println("getPhase2 -> " + phaser.getPhase());
                phaser.arriveAndAwaitAdvance();

                System.out.println(no + " start 3 ");
                TimeUnit.SECONDS.sleep(2);
                System.out.println(no + " end 3 ");
                System.out.println("getPhase3 -> " + phaser.getPhase());
                phaser.arriveAndAwaitAdvance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();
        }
    }
}
