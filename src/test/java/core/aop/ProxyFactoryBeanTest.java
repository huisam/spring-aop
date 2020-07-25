package core.aop;

import core.di.factory.example.ProxyTarget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.UpperCaseInterceptor;
import study.proxy.matcher.MethodMatcher;
import study.proxy.matcher.StartMethodMatcher;

import static org.assertj.core.api.Assertions.assertThat;

class ProxyFactoryBeanTest {
    @Test
    @DisplayName("ProxyFactoryBean이 올바르게 Class Type을 가져오는지 테스트")
    void test_proxy_factory_bean() {
        /* given */
        ProxyTarget proxyTarget = new ProxyTarget();
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(proxyTarget);

        /* when */
        final Class<?> objectType = proxyFactoryBean.getObjectType();

        /* then */
        assertThat(objectType).isEqualTo(ProxyTarget.class);
    }

    @Test
    @DisplayName("ProxyFactoryBean에 Callback을 적용해서 Proxy Target을 생성하기")
    void test_proxy_factory_bean_callBack() {
        /* given */
        ProxyTarget proxyTarget = new ProxyTarget();
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(proxyTarget);

        MethodMatcher methodMatcher = new StartMethodMatcher();
        proxyFactoryBean.addCallBackMatchers(new UpperCaseInterceptor(methodMatcher));

        /* when */
        final ProxyTarget proxyBean = (ProxyTarget) proxyFactoryBean.getObject();

        /* then */
        assertThat(proxyBean.start("huisam")).isEqualTo("START FROM HUISAM");
    }
}