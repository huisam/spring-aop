package core.aop.tx;

import core.annotation.Transactional;
import core.aop.MethodMatcher;

import java.lang.reflect.Method;

public class TxMethodMatcher implements MethodMatcher {
    @Override
    public boolean matches(Method method) {
        return method.isAnnotationPresent(Transactional.class);
    }
}
