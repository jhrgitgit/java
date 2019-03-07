package com.xm.wangwenjun.concurrency.chapter2;

/***************************************
 * @author:Alex Wang
 * @Date:2017/2/15 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class TicketWindow extends Thread {

    private final String name;

    private static final int MAX = 50;

    private static int index = 1;

    public TicketWindow(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        while (index <= MAX) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("柜台：" + name + "当前的号码是:" + (index++));
        }
    }
}
