package core.mvc.advice;

import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class DefaultExceptionHandler implements ExceptionHandler {
    private final Method method;

    public DefaultExceptionHandler(Method exceptionHandlerMethod) {
        this.method = exceptionHandlerMethod;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws ReflectiveOperationException {
        final Object instance = method.getDeclaringClass().getConstructor().newInstance();

        return (ModelAndView) method.invoke(instance, request, response);
    }
}
