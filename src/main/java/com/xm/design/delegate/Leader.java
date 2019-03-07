package com.xm.design.delegate;

import java.util.HashMap;
import java.util.Map;

public class Leader implements ITarget {

    private Map<String, ITarget> targets = new HashMap<>();

    /**
     * 项目经理持有小组成员可供选择，类似策略模式
     */
    public Leader() {
        targets.put("加密", new TargetA());
        targets.put("登录", new TargetB());
    }

    public void doSomething(String command) {
        targets.get(command).doSomething(command);
    }
}
