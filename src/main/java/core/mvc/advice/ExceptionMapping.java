package core.mvc.advice;

public interface ExceptionMapping {
    void initialize();

    ExceptionHandler getHandler(Throwable throwable);
}
