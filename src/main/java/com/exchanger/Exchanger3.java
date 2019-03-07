package com.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 一对儿线程交换数据
 */
public class Exchanger3 {
    public static void main(String[] args) {
        final Exchanger<Integer> exchanger = new Exchanger<Integer>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                AtomicReference<Integer> value = new AtomicReference<Integer>(1);
                try {
                    while(true){
                        value.set(exchanger.exchange(value.get()));
                        System.out.println("A has value: " + value.get());
                        TimeUnit.SECONDS.sleep(3);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"==A==").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                AtomicReference<Integer> value = new AtomicReference<Integer>(2);
                try {
                    while(true){
                        value.set(exchanger.exchange(value.get()));
                        System.out.println("B has value: " + value.get());
                        TimeUnit.SECONDS.sleep(2);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"==B==").start();
    }
}
