package core.aop.factorybean;

import core.di.beans.factory.support.BeanFactoryUtils;
import core.di.context.ApplicationContext;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Constructor;
import java.util.Arrays;

@RequiredArgsConstructor
public abstract class ProxyFactoryBean implements FactoryBean<Object> {

    private final ApplicationContext applicationContext;

    private final Object target;

    private final Class<?> objectType;

    @Override
    public Object getObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(getMethodInterceptor());

        final Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(target.getClass());

        final Class<?>[] parameterTypes = injectedConstructor.getParameterTypes();
        Object[] argument = findConcreteBeanParametersFrom(parameterTypes);

        return enhancer.create(parameterTypes, argument);
    }

    protected abstract MethodInterceptor getMethodInterceptor();

    private Object[] findConcreteBeanParametersFrom(Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes)
                .map(this::findBeanFromContext)
                .toArray();
    }

    private Object findBeanFromContext(Class<?> beanClass) {
        Object bean = applicationContext.getBean(beanClass);
        if (bean == null) {
            throw new IllegalArgumentException("Bean doesn't exists in context");
        }
        return bean;
    }

    @Override
    public Class<?> getObjectType() {
        return this.objectType;
    }
}
