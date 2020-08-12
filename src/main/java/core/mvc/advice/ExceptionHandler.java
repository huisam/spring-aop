package core.mvc.advice;

import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExceptionHandler {

    ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws ReflectiveOperationException;
}
