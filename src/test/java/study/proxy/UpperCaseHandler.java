package study.proxy;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class UpperCaseHandler implements InvocationHandler {

    private final Car car;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String methodName = (String) method.invoke(car, args);

        return methodName.toUpperCase();
    }
}
