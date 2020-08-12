package core.mvc.advice;

import com.google.common.collect.Maps;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerAdviceExceptionMapping implements ExceptionMapping {

    private static final Class<ControllerAdvice> CONTROLLER_ADVICE_ANNOTATION = ControllerAdvice.class;
    public static final Class<ExceptionHandle> EXCEPTION_HANDLE_ANNOTATION = ExceptionHandle.class;
    private final Map<Class<? extends Throwable>, ExceptionHandler> handlers = Maps.newHashMap();
    private final Object[] basePackage;

    public ControllerAdviceExceptionMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        Reflections reflections = new Reflections(basePackage);

        final Set<Class<?>> controllerAdviceClasses = reflections.getTypesAnnotatedWith(CONTROLLER_ADVICE_ANNOTATION, true);
        for (Class<?> controllerAdviceClass : controllerAdviceClasses) {
            final List<Method> exceptionHandlerMethods = Arrays.stream(controllerAdviceClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(EXCEPTION_HANDLE_ANNOTATION))
                    .collect(Collectors.toList());

            for (Method exceptionHandlerMethod : exceptionHandlerMethods) {
                final Class<? extends Throwable> exceptionClass = exceptionHandlerMethod.getAnnotation(EXCEPTION_HANDLE_ANNOTATION).exception();

                handlers.put(exceptionClass, new DefaultExceptionHandler(exceptionHandlerMethod));
            }
        }
    }

    @Override
    public core.mvc.advice.ExceptionHandler getHandler(Class<? extends Exception> exceptionClass) {
        return handlers.get(exceptionClass);
    }
}
