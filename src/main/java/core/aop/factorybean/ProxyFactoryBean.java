package core.aop.factorybean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public abstract class ProxyFactoryBean implements FactoryBean<Object> {

    @Override
    public Object getObject() {
        return Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{getObjectType()},
                getInvocationHandler()
        );
    }

    protected abstract InvocationHandler getInvocationHandler();

    public abstract Class<?> getObjectType();
}
