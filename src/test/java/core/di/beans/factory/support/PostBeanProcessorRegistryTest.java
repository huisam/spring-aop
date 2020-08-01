package core.di.beans.factory.support;

import core.aop.factorybean.ProxyFactoryBean;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;

import static org.assertj.core.api.Assertions.assertThat;

class PostBeanProcessorRegistryTest {

    @Test
    @DisplayName("Proxy Processor 테스트")
    void proxy_processor_test() {
        /* given */
        PostBeanProcessorRegistry postBeanProcessorRegistry = new PostBeanProcessorRegistry();
        postBeanProcessorRegistry.addProcessor((clazz, bean) -> new UpperCaseProxyFactoryBean(bean, clazz).getObject());
        postBeanProcessorRegistry.addProcessor((clazz, bean) -> new QuestionMarkProxyFactoryBean(bean, clazz).getObject());

        /* when */
        Hello processBean = (Hello) postBeanProcessorRegistry.process(Hello.class, new HelloBean());

        /* then */
        assertThat(processBean).isNotNull();
        assertThat(processBean.say("huisam")).isEqualTo("HELLO HUISAM?");
    }

    @RequiredArgsConstructor
    public static class UpperCaseProxyFactoryBean extends ProxyFactoryBean {

        private final Object target;
        private final Class<?> objectType;

        @Override
        protected InvocationHandler getInvocationHandler() {
            return (proxy, method, args) -> {
                String result = (String) method.invoke(target, args);
                return result.toUpperCase();
            };
        }

        @Override
        public Class<?> getObjectType() {
            return this.objectType;
        }
    }

    @RequiredArgsConstructor
    public static class QuestionMarkProxyFactoryBean extends ProxyFactoryBean {

        private final Object target;
        private final Class<?> objectType;

        @Override
        protected InvocationHandler getInvocationHandler() {
            return (proxy, method, args) -> {
                final String result = (String) method.invoke(target, args);
                return result + "?";
            };
        }

        @Override
        public Class<?> getObjectType() {
            return objectType;
        }
    }

    public interface Hello {
        String say(String name);
    }

    public static class HelloBean implements Hello {
        @Override
        public String say(String name) {
            return "hello " + name;
        }
    }
}