package core.di.beans.factory.support;

import java.util.ArrayList;
import java.util.List;

public class PostBeanProcessorRegistry {

    private final List<PostBeanProcessor> processors = new ArrayList<>();

    public void addProcessor(PostBeanProcessor postBeanProcessor) {
        processors.add(postBeanProcessor);
    }

    public Object process(Class<?> clazz, Object bean) {
        Object processedBean = bean;
        for (PostBeanProcessor processor : processors) {
            if (processor.supports(clazz, bean)) {
                processedBean = processor.process(clazz, processedBean);
            }
        }

        return processedBean;
    }
}
