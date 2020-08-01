package core.di.beans.factory.support;

import core.aop.tx.TxProxyFactoryBean;

import java.util.ArrayList;
import java.util.List;

public class PostBeanProcessorRegistry {

    private final List<PostBeanProcessor> processors = new ArrayList<>();

    public PostBeanProcessorRegistry() {
        processors.add((clazz, bean) -> new TxProxyFactoryBean(bean, clazz).getObject());
    }

    public void addProcessor(PostBeanProcessor postBeanProcessor) {
        processors.add(postBeanProcessor);
    }

    public Object process(Class<?> clazz, Object bean) {
        Object processedBean = bean;
        for (PostBeanProcessor processor : processors) {
            processedBean = processor.process(clazz, processedBean);
        }

        return processedBean;
    }
}
