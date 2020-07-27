package study.proxy;

import core.aop.pointcut.MethodMatcher;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class UpperCaseHandler implements InvocationHandler {

    private final Car car;
    private final MethodMatcher methodMatcher;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String methodName = (String) method.invoke(car, args);
        if (methodMatcher.matches(method)) {
            return methodName.toUpperCase();
        }
        return methodName;
    }
}
