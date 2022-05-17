package com.simple.system.todo.advice;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ItemControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> catchIllegalArgumentException(IllegalArgumentException argumentException) {
		return new ResponseEntity<String>(
				"Please validate the Input Parameters with Request Submitted ! " + argumentException.getMessage(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidAttributesException.class)
	public ResponseEntity<String> catchInvalidAttributesException(InvalidAttributesException attributesException) {
		return new ResponseEntity<String>(
				"Please validate the Input Parameters with Request Submitted ! " + attributesException.getMessage(),
				HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDate.now());
		body.put("status", status.value());

		return new ResponseEntity<>(body, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDate.now());
		body.put("status", status.value());

		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, headers, status);

	}

}
