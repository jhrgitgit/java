package com.xm.design.delegate;

public class TargetB implements ITarget {
    @Override
    public void doSomething(String command) {
        System.out.println("我是员工B，现在开始干" + command + "");
    }
}
