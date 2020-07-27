package core.di.beans.factory.support;

import core.aop.FactoryBean;
import core.aop.MethodMatcher;
import core.di.factory.example.ProxyTarget;
import net.sf.cglib.proxy.Enhancer;
import study.proxy.UpperCaseInterceptor;
import study.proxy.matcher.StartMethodMatcher;

public class ProxyTargetBeanFactory implements FactoryBean<ProxyTarget> {

    private final Enhancer enhancer = new Enhancer();

    @Override
    public ProxyTarget getObject() {
        MethodMatcher methodMatcher = new StartMethodMatcher();

        enhancer.setSuperclass(ProxyTarget.class);
        enhancer.setCallback(new UpperCaseInterceptor(methodMatcher));

        return (ProxyTarget) enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return ProxyTarget.class;
    }
}
