package core.aop.tx;

import core.aop.factorybean.ProxyFactoryBean;
import core.di.context.ApplicationContext;
import org.springframework.cglib.proxy.MethodInterceptor;

public class TxProxyFactoryBean extends ProxyFactoryBean {

    private ApplicationContext applicationContext;

    public TxProxyFactoryBean(ApplicationContext applicationContext, Object target, Class<?> objectType) {
        super(applicationContext, target, objectType);
        this.applicationContext = applicationContext;
    }

    @Override
    protected MethodInterceptor getMethodInterceptor() {
        return new TxMethodInterceptor(applicationContext, new TxPointCut());
    }
}
