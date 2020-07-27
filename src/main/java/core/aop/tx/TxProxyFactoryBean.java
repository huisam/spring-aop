package core.aop.tx;

import core.aop.factorybean.ProxyFactoryBean;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;

@RequiredArgsConstructor
public class TxProxyFactoryBean extends ProxyFactoryBean {

    private final Object target;
    private final Class<?> objectType;

    @Override
    protected InvocationHandler getInvocationHandler() {
        return new TxInvocationHandler(target, new TxPointCut());
    }

    @Override
    public Class<?> getObjectType() {
        return objectType;
    }
}
