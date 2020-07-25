package core.di.beans.factory.support;

import core.annotation.Bean;
import core.annotation.Configuration;

@Configuration
public class ProxyConfig {

    @Bean
    public ProxyTargetBeanFactory proxyTargetBeanFactory() {
        return new ProxyTargetBeanFactory();
    }
}
