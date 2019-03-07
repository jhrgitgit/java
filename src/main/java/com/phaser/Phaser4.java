package com.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * onAdvance
 */
public class Phaser4 {
    public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(2) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                return false;
            }
        };

        new onAdvanceTask("A",phaser).start();
        new onAdvanceTask("B",phaser).start();

        TimeUnit.SECONDS.sleep(2);

        System.out.println(phaser.getUnarrivedParties());
        System.out.println(phaser.getArrivedParties());

    }

    static class onAdvanceTask extends Thread {

        private final Phaser phaser;

        onAdvanceTask(String name, Phaser phaser) {
            super(name);
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(getName() + "I am start1 phaser = " + phaser.getPhase());
            phaser.arriveAndAwaitAdvance();
            System.out.println(getName() + "I am end1 phaser = " + phaser.getPhase());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            if("A".equals(getName())){
                System.out.println(getName() + "I am start2 phaser = " + phaser.getPhase());
                phaser.arriveAndAwaitAdvance();
                System.out.println(getName() + "I am end2 phaser = " + phaser.getPhase());
//            }

        }
    }

}
