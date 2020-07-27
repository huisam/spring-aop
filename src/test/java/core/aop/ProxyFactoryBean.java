package core.aop;

import core.aop.factorybean.FactoryBean;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

public class ProxyFactoryBean implements FactoryBean<Object> {

    private final Enhancer enhancer = new Enhancer();
    private Object object;

    public ProxyFactoryBean(Object object) {
        enhancer.setSuperclass(object.getClass());
        this.object = object;
    }

    public void addCallBackMatchers(Callback... callbacks) {
        enhancer.setCallbacks(callbacks);
    }

    @Override
    public Object getObject() {
        return enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return object.getClass();
    }
}
