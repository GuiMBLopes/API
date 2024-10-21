package br.org.serratec.FinalAPI.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler{

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> erros = new ArrayList<>();
		for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
			erros.add(fe.getField() + ": " + fe.getDefaultMessage());
		}
		ErroResposta erroResposta = new ErroResposta(status.value(), "Existe campos inválido!"
				, LocalDateTime.now(), erros);
		
		return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
	}
	
	@ExceptionHandler(CadastroException.class)
	private ResponseEntity<Object> handleCadastroException(CadastroException ex) {
			return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}
	
	@ExceptionHandler(FollowException.class)
	private ResponseEntity<Object> handleFollowException(FollowException ex){
		return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}
}
