package core.mvc.advice;

public interface ExceptionMapping {
    void initialize();

    ExceptionHandler getHandler(Class<? extends Exception> exceptionClass);
}
