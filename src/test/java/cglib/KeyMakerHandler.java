package cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Objects;

public class KeyMakerHandler implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) {
        if ("build".equals(method.getName())) {
            final Key key = (Key) objects[0];
            final String date = (String) objects[1];

            return Objects.isNull(key.getPrevious()) ? date : key.getPrevious();
        }
        throw new IllegalArgumentException("Not Supported Method Name : " + method.getName());
    }
}
