package core.mvc;

import core.mvc.advice.ExceptionHandler;
import core.mvc.advice.ExceptionHandlingRegistry;
import core.mvc.advice.ExceptionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry();

    private HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();

    private ExceptionHandlingRegistry exceptionHandlingRegistry = new ExceptionHandlingRegistry();

    private HandlerExecutor handlerExecutor = new HandlerExecutor(handlerAdapterRegistry);

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappingRegistry.addHandlerMpping(handlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            Optional<Object> maybeHandler = handlerMappingRegistry.getHandler(req);
            if (!maybeHandler.isPresent()) {
                resp.setStatus(HttpStatus.NOT_FOUND.value());
                return;
            }


            ModelAndView mav = handlerExecutor.handle(req, resp, maybeHandler.get());
            render(mav, req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            handleException(req, resp, e);
        }
    }

    private void handleException(HttpServletRequest req, HttpServletResponse resp, Throwable e) throws ServletException {
        final ExceptionHandler exceptionHandler = exceptionHandlingRegistry.getHandler(e)
                .orElseThrow(() -> new ServletException(e.getMessage()));

        try {
            final ModelAndView modelAndView = exceptionHandler.handle(req, resp);
            render(modelAndView, req, resp);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }

    public void addExceptionMapping(ExceptionMapping exceptionMapping) {
        exceptionHandlingRegistry.addExceptionMapping(exceptionMapping);
    }
}
