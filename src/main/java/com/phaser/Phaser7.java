package com.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 打断，
 */
public class Phaser7 {
    public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(3);

        Thread thread = new Thread(() -> {
            try {
                //phaser不相等时会立即返回
                phaser.awaitAdvanceInterruptibly(phaser.getPhase());
                //可以timeout等待(0,0,timeout)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        System.out.println("===============================");
        TimeUnit.SECONDS.sleep(3);
        thread.interrupt();
        System.out.println("====thread.interrupt===");
    }

}
