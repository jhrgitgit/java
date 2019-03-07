package com.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 铁人三项(4个正常，一个伤病退出)
 */
public class Phaser3 {
    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);

        for (int i = 1; i < 5; i++) {
            new Athletes(phaser, i).start();
        }
        new InjureAthletes(phaser, 6).start();
    }

    static class InjureAthletes extends Thread {
        private final Phaser phaser;
        private final int no;

        InjureAthletes(Phaser phaser, int no) {
            this.phaser = phaser;
            this.no = no;
        }

        @Override
        public void run() {

            try {
                sport(phaser, no + " start 1 ", no + " end 1 ");

                sport(phaser, no + " start 2 ", no + " end 2 ");

                System.out.println("I am injured");
                //取消注册
                phaser.arriveAndDeregister();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
                sport(phaser, no + " start 1 ", no + " end 1 ");

                sport(phaser, no + " start 2 ", no + " end 2 ");

                sport(phaser, no + " start 3 ", no + " end 3 ");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    private static void sport(Phaser phaser, String x, String x2) throws InterruptedException {
        System.out.println(x);
        TimeUnit.SECONDS.sleep(2);
        System.out.println(x2);
        phaser.arriveAndAwaitAdvance();
    }
}
