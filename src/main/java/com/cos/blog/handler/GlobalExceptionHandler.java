package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
//	@ExceptionHandler(value=IllegalArgumentException.class)
//	public String handleArgumentException(IllegalArgumentException e) {
//		return "<h1>"+e.getMessage()+"</h1>";
//	}
	
//	@ExceptionHandler(value=Exception.class)
//	public String handleException(Exception e) {
//		return "<h1>알수없는 오류가 발생하였습니다.["+e.getMessage()+"]</h1>";
//	}	
	
	@ExceptionHandler(value=Exception.class)
	public ResponseDto<String> handleException(Exception e) {
		e.printStackTrace();
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getCause().getCause().getMessage());
	}		
}
