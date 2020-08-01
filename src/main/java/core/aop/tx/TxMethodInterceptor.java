package core.aop.tx;

import core.aop.pointcut.ClassFilter;
import core.aop.pointcut.MethodMatcher;
import core.aop.pointcut.Pointcut;
import core.aop.tx.connection.ConnectionUtils;
import core.di.context.ApplicationContext;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;

@RequiredArgsConstructor
public class TxMethodInterceptor implements MethodInterceptor {

    private final ApplicationContext applicationContext;
    private final Pointcut pointcut;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Class<?> targetClass = o.getClass();
        if (match(targetClass) || matchMethod(method)) {
            return applyTransactionProcess(o, objects, methodProxy);
        }
        return methodProxy.invoke(o, objects);
    }

    private Object applyTransactionProcess(Object o, Object[] objects, MethodProxy methodProxy) throws Throwable {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        Connection connection = ConnectionUtils.getConnection(dataSource);

        try {
            final Object result = methodProxy.invoke(o, objects);
            connection.commit();

            return result;
        } catch (Exception e) {
            connection.rollback();
            throw new IllegalStateException("Transaction is rollback", e);
        } finally {
            ConnectionUtils.closeConnection();
        }
    }

    private boolean matchMethod(Method method) {
        MethodMatcher methodMatcher = pointcut.getMethodMatcher();

        return methodMatcher.matches(method);
    }

    private boolean match(Class<?> targetClass) {
        ClassFilter classFilter = pointcut.getClassFilter();

        return classFilter.matches(targetClass);
    }
}
