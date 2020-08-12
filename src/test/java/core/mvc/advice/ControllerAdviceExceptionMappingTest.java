package core.mvc.advice;

import core.mvc.ModelAndView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerAdviceExceptionMappingTest {

    @Test
    @DisplayName("특정 package를 기준으로 ControllerAdvice를 읽어서 ExceptionHandler를 등록한다")
    void controller_advice_test() throws ReflectiveOperationException {
        /* given */
        ControllerAdviceExceptionMapping exceptionMapping = new ControllerAdviceExceptionMapping("core.mvc.advice");

        /* when */
        exceptionMapping.initialize();

        /* then */
        ExceptionHandler handler = exceptionMapping.getHandler(RuntimeException.class);
        final ModelAndView modelAndView = handler.handle(new MockHttpServletRequest(), new MockHttpServletResponse());

        final Object result = modelAndView.getObject("test");
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("value");
    }
}