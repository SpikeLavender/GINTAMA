package com.natsumes.wezard.exception;

//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.enums.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;


@Slf4j
@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Response handle(RuntimeException e) {
        log.error("异常原因, {} ", e.getMessage());
        return Response.error(ResponseEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public Response userLoginExceptionHandle() {
        return Response.error(ResponseEnum.NEED_LOGIN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Response notValidExceptionHandle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        log.error("注册提交的参数有误, {} {}", Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                bindingResult.getFieldError().getDefaultMessage());
        return Response.error(ResponseEnum.PARAM_ERROR, bindingResult);
    }
}
