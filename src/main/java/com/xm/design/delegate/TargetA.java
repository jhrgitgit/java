package com.xm.design.delegate;

public class TargetA implements ITarget {
    @Override
    public void doSomething(String command) {
        System.out.println("我是员工A，现在开始干" + command + "");
    }
}
