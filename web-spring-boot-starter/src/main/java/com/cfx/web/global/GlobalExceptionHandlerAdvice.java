package com.cfx.web.global;


import com.cfx.common.api.DataIMessage;
import com.cfx.common.api.IMessage;
import com.cfx.common.exception.ServiceException;
import com.cfx.common.utils.CommUtils;
import com.cfx.common.writer.ApiResponse;
import com.cfx.common.writer.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerAdvice.class);

    /**
     * 方法参数校验失败异常处理.
     *
     * @param mex {@link org.springframework.web.bind.MethodArgumentNotValidException}
     * @return 参数校验失败统一响应处理 {@link com.cfx.common.api.IMessage}
     * @see MethodArgumentNotValidException#getBindingResult()
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public IMessage methodArgumentNotValidException(MethodArgumentNotValidException mex) {
        if (mex != null && mex.getBindingResult().hasErrors())
            return buildIllegalArgument(ExceptionUtils.buildExceptionMessage(mex));
        else
            return ApiResponse.failed(Errors.ILLEGAL_ARGUMENT);

    }

    /**
     * 参数绑定错误异常处理.
     *
     * @param be {@link org.springframework.validation.BindException}
     * @return 包含参数绑定错误信息的 {@link IMessage}
     */
    @ExceptionHandler(value = org.springframework.validation.BindException.class)
    public IMessage bindExceptionHandler(org.springframework.validation.BindException be) {
        return buildIllegalArgument(ExceptionUtils.buildExceptionMessage(be));
    }


    /**
     * 单参数校验错误异常处理.
     *
     * @param e {@link ConstraintViolationException}
     * @return 包含单参数校验失败信息的 {@link IMessage}
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public IMessage constraintViolationExceptionHandler(ConstraintViolationException e) {
        return buildIllegalArgument(ExceptionUtils.buildExceptionMessage(e));
    }

    /**
     * 参数类型错误异常处理.
     *
     * @param e {@link MethodArgumentTypeMismatchException}
     * @return 包含参数类型错误信息的 {@link IMessage}
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public IMessage methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        return buildIllegalArgument(ExceptionUtils.buildExceptionMessage(e));
    }

    /**
     * 自定义业务异常处理.
     *
     * @param e {@link ServiceException}
     * @return 包含业务异常信息的 {@link IMessage}
     */
    @ExceptionHandler(value = ServiceException.class)
    public IMessage serviceExceptionHandler(ServiceException e) {
        Throwable causeThrowable = e.getCause();
        if (causeThrowable != null) {
            LOGGER.error("发生由其他异常导致的业务异常", causeThrowable);
        }
        return DataIMessage.getInstance(e.getStatus(), e.getMessage());
    }

    /**
     * 处理参数错误异常.
     *
     * @param e {@link IllegalArgumentException}
     * @return 包含异常信息的 {@link IMessage}
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public IMessage illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return buildIllegalArgument(e.getMessage());
    }

    /**
     * 拦截 Exception
     *
     * @param e {@link Exception}
     * @return 包含异常信息的 {@link IMessage}
     */
    @ExceptionHandler(value = Exception.class)
    public IMessage exceptionHandler(Exception e) {
        return ApiResponse.failed(Errors.INTERNAL_SERVER_ERROR.getStatus(), e.getClass().getName(), e.getMessage());
    }


    private IMessage buildIllegalArgument(String message) {
        return ApiResponse.failed(Errors.ILLEGAL_ARGUMENT, message);
    }

    protected static abstract class ExceptionUtils {

        public static String buildExceptionMessage(MethodArgumentTypeMismatchException e) {

            if (null == e) return CommUtils.EMPTY_CHAR;

            StringBuilder sb = new StringBuilder();
            String name = e.getName();
            Class<?> expectType = e.getRequiredType();
            String actualType = ClassUtils.getDescriptiveType(e.getValue());
            Object value = e.getValue();

            sb.append("参数类型错误, 参数名: '").append(name).append("'");
            if (null != value) {
                sb.append(", 输入值: '").append(value).append("'");
            }
            if (null != expectType) {
                sb.append(", 期望类型: '").append(ClassUtils.getQualifiedName(expectType)).append("'");
            }
            sb.append(", 输入类型: '").append(actualType).append("'");
            return sb.toString();
        }

        public static String buildExceptionMessage(org.springframework.validation.BindException e) {
            return buildBindingResultMessage(e.getBindingResult());
        }

        public static String buildExceptionMessage(MethodArgumentNotValidException e) {
            return buildBindingResultMessage(e.getBindingResult());
        }

        public static String buildExceptionMessage(ConstraintViolationException e) {
            if (e == null || CollectionUtils.isEmpty(e.getConstraintViolations()))
                return CommUtils.EMPTY_CHAR;

            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            StringBuilder sb = new StringBuilder(violations.size() * 12);
            sb.append("参数校验失败: ");
            violations.forEach(error -> {
                sb.append("[");
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    sb.append(fieldError.getField()).append(":");
                }
                sb.append(error.getMessage());
                sb.append("] ");
            });
            return sb.toString();
        }
    }

    private static String buildBindingResultMessage(BindingResult bindingResult) {
        if (bindingResult == null)
            return CommUtils.EMPTY_CHAR;

        List<ObjectError> errors = bindingResult.getAllErrors();
        StringBuilder sb = new StringBuilder();
        sb.append("参数校验失败: ");
        errors.forEach(error -> {
            sb.append("[");
            if (error instanceof FieldError)
                sb.append(((FieldError) error).getField()).append(":");
            sb.append(error.getDefaultMessage());
            sb.append("] ");
        });
        return sb.toString();
    }
}