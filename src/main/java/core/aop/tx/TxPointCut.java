package core.aop.tx;

import core.aop.ClassFilter;
import core.aop.MethodMatcher;
import core.aop.Pointcut;

public class TxPointCut implements Pointcut {

    private final ClassFilter classFilter = new TxClassFilter();
    private final MethodMatcher methodMatcher = new TxMethodMatcher();

    @Override
    public ClassFilter getClassFilter() {
        return classFilter;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }
}
