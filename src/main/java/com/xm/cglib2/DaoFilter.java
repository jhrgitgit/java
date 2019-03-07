package com.xm.cglib2;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class DaoFilter implements CallbackFilter {
    @Override
    public int accept(Method method) {
        if ("select".equals(method.getName())) {
            return 0;
        }
        return 1;

    }
}
