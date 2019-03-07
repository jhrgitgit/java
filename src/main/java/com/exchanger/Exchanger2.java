package com.exchanger;

import java.util.concurrent.Exchanger;

/**
 * 一对儿线程交换数据
 */
public class Exchanger2 {
    public static void main(String[] args) {
        final Exchanger<Object> exchanger = new Exchanger<Object>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Object object = new Object();
                System.out.println("A will send the object " + object);
                try {
                    Object result = exchanger.exchange(object);
                    System.out.println("A get value " + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"==A==").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Object object = new Object();
                System.out.println("B will send the object " + object);
                try {
                    Object result = exchanger.exchange(object);
                    System.out.println("B get value " + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"==B==").start();
    }
}
