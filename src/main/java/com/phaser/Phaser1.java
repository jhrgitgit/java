package com.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Phaser1 {
    public static void main(String[] args) {
        final Phaser phaser = new Phaser();

        IntStream.rangeClosed(1, 5).boxed().map(i -> phaser).forEach(Task::new);
        phaser.register();
        phaser.arriveAndAwaitAdvance();
        System.out.println("all of worker finished");
    }

    static class Task extends Thread {
        private final Phaser phaser;

        Task(Phaser phaser) {
            this.phaser = phaser;
            this.phaser.register();
            start();
        }

        @Override
        public void run() {
            System.out.println("the worker[" + getName() + "]");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();
        }
    }
}
