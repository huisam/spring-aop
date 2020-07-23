package study.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxyTest {
    @Test
    @DisplayName("Jdk Dynamic Proxy 테스트")
    void jdkProxyTest() {
        /* given */
        InvocationHandler invocationHandler = new UpperCaseHandler(new CarTarget());

        /* when */
        final Car proxiedCar = (Car) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Car.class},
                invocationHandler
        );

        /* then */
        assertThat(proxiedCar.start("huisam")).isEqualTo("CAR HUISAM STARTED!");
        assertThat(proxiedCar.stop("huisam")).isEqualTo("CAR HUISAM STOPPED!");
    }
}