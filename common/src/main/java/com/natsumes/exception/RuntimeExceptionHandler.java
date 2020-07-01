package com.natsumes.exception;

import com.natsumes.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

import static com.natsumes.enums.ResponseEnum.*;

@Slf4j
@ControllerAdvice
public class RuntimeExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	//@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseVo handle(RuntimeException e) {
		return ResponseVo.error(SYSTEM_ERROR, e.getMessage());
	}

	@ExceptionHandler(UserLoginException.class)
	@ResponseBody
	public ResponseVo userLoginExceptionHandle() {
		return ResponseVo.error(NEED_LOGIN);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseVo notValidExceptionHandle(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		log.error("注册提交的参数有误, {} {}", Objects.requireNonNull(bindingResult.getFieldError()).getField(),
				bindingResult.getFieldError().getDefaultMessage());
		return ResponseVo.error(PARAM_ERROR, bindingResult);
	}
}
