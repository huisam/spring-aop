package study.proxy;

import core.aop.MethodMatcher;
import lombok.RequiredArgsConstructor;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@RequiredArgsConstructor
public class UpperCaseInterceptor implements MethodInterceptor {

    private final MethodMatcher methodMatcher;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        final String methodName = (String) proxy.invokeSuper(obj, args);
        if (methodMatcher.matches(method)) {
            return methodName.toUpperCase();
        }
        return methodName;
    }
}
