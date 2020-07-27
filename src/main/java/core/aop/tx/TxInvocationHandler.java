package core.aop.tx;

import core.aop.ClassFilter;
import core.aop.MethodMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

@RequiredArgsConstructor
public class TxInvocationHandler implements InvocationHandler {

    private final Object target;

    private final TxPointCut txPointCut;

    private DataSource dataSource;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> clazz = target.getClass();
        if (matchClass(clazz) || methodMatch(method)) {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            try {
                connection.setAutoCommit(false);

                final Object result = method.invoke(target, args);
                connection.commit();

                return result;
            } catch (Exception e) {
                connection.rollback();
                return new IllegalStateException("Transaction is roll back, ", e);
            } finally {
                connection.close();
            }
        }
        return method.invoke(target, args);
    }

    private boolean matchClass(Class<?> clazz) {
        final ClassFilter classFilter = txPointCut.getClassFilter();

        return classFilter.matches(clazz);
    }

    private boolean methodMatch(Method method) {
        final MethodMatcher methodMatcher = txPointCut.getMethodMatcher();

        return methodMatcher.matches(method);
    }
}
