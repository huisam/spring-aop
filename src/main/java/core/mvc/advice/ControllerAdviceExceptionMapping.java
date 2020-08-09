package core.mvc.advice;

import com.google.common.collect.Maps;

import java.util.Map;

public class ControllerAdviceExceptionMapping implements ExceptionMapping {

    private final Map<? extends Throwable, ExceptionHandler> handlers = Maps.newHashMap();

    @Override
    public void initialize() {
        //TODO must override this method
    }

    @Override
    public core.mvc.advice.ExceptionHandler getHandler(Throwable throwable) {
        return null;
    }
}
