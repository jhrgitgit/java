package com.exchanger;

import java.util.concurrent.Exchanger;

/**
 * 一对儿线程交换数据
 */
public class Exchanger1 {
    public static void main(String[] args) {
        final Exchanger<String> exchanger = new Exchanger<String>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start.");
                try {
                    String result = exchanger.exchange("I am come from T-A");
                    System.out.println(Thread.currentThread().getName() + " get value " + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end.");
            }
        },"==A==").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start.");
                try {
                    String result = exchanger.exchange("I am come from T-B");
                    System.out.println(Thread.currentThread().getName() + " get value " + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end.");
            }
        },"==B==").start();
    }
}
