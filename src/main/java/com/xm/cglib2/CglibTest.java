package com.xm.cglib2;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.junit.jupiter.api.Test;

public class CglibTest {

    @Test
    public void testCglib() {
        DaoProxy daoProxy = new DaoProxy();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Dao.class);
        enhancer.setCallback(daoProxy);
        enhancer.setInterceptDuringConstruction(false);

        Dao dao = (Dao) enhancer.create();
        dao.update();
        dao.select();
    }

    @Test
    public void testCglib2() {
        DaoProxy daoProxy = new DaoProxy();
        DaoAnotherProxy daoAnotherProxy = new DaoAnotherProxy();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Dao.class);
        enhancer.setCallbacks(new Callback[]{daoProxy, daoAnotherProxy, NoOp.INSTANCE});
        enhancer.setCallbackFilter(new DaoFilter());
        enhancer.setInterceptDuringConstruction(false);

        Dao dao = (Dao) enhancer.create();
        dao.update();
        dao.select();
    }

}
