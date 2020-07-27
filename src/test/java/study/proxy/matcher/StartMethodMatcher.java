package study.proxy.matcher;

import core.aop.MethodMatcher;

import java.lang.reflect.Method;

public class StartMethodMatcher implements MethodMatcher {
    private static final String TALK_PREFIX = "start";

    @Override
    public boolean matches(Method method) {
        final String methodName = method.getName();

        return methodName.startsWith(TALK_PREFIX);
    }
}
