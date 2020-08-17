package core.mvc.advice;

import core.mvc.ModelAndView;

@ControllerAdvice
public class TestControllerAdvice {

    @ExceptionHandle(exception = RuntimeException.class)
    public ModelAndView handleException() {
        return new ModelAndView().addObject("test", "value");
    }
}
