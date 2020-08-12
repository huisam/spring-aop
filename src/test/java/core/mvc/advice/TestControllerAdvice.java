package core.mvc.advice;

import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class TestControllerAdvice {

    @ExceptionHandle(exception = RuntimeException.class)
    public ModelAndView handleException(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView().addObject("test", "value");
    }
}
