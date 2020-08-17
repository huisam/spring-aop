package core.mvc.advice;

import core.mvc.ModelAndView;
import core.mvc.tobe.ArgumentMatcher;
import core.mvc.tobe.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class DefaultExceptionHandler implements ExceptionHandler {
    private final Method method;
    private final ArgumentMatcher argumentMatcher;

    public DefaultExceptionHandler(Method exceptionHandlerMethod, ArgumentMatcher argumentMatcher) {
        this.method = exceptionHandlerMethod;
        this.argumentMatcher = argumentMatcher;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws ReflectiveOperationException {
        final MethodParameter[] methodParameters = argumentMatcher.getMethodParameters(method);
        final Object[] arguments = new Object[methodParameters.length];

        for (int i = 0; i < methodParameters.length; i++) {
            arguments[i] = argumentMatcher.resolveArgument(methodParameters[i], request, response);
        }

        final Object instance = method.getDeclaringClass().getConstructor().newInstance();
        return (ModelAndView) method.invoke(instance, arguments);
    }
}
