package study.proxy;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.matcher.StartMethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxyTest {
    @Test
    @DisplayName("Jdk Dynamic Proxy 테스트")
    void jdkProxyTest() {
        /* given */
        InvocationHandler invocationHandler = new UpperCaseHandler(new CarTarget(), new StartMethodMatcher());

        /* when */
        final Car proxiedCar = (Car) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Car.class},
                invocationHandler
        );

        /* then */
        assertThat(proxiedCar.start("huisam")).isEqualTo("CAR HUISAM STARTED!");
        assertThat(proxiedCar.stop("huisam")).isEqualTo("Car huisam stopped!");
    }

    @Test
    @DisplayName("cglib Proxy 테스트")
    void cglibProxyTest() {
        /* given */
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CarTarget.class);
        enhancer.setCallback(new UpperCaseInterceptor(new StartMethodMatcher()));

        /* when */
        final Car proxiedCar = (Car) enhancer.create();

        /* then */
        assertThat(proxiedCar.start("huisam")).isEqualTo("CAR HUISAM STARTED!");
        assertThat(proxiedCar.stop("huisam")).isEqualTo("Car huisam stopped!");
    }
}
