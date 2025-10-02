package dev.decafmango.service1.exception;

import dev.decafmango.common.exception.ApplicationException;
import dev.decafmango.common.exception.ApplicationExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class, produces = "application/xml")
    public ResponseEntity handleValidationExceptions(
            MethodArgumentNotValidException e) {
        StringBuilder errorMessage = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String message = error.getDefaultMessage();
            errorMessage.append(message).append("\n");
        });
        errorMessage.delete(errorMessage.length() - 1, errorMessage.length());

        ApplicationExceptionDto applicationExceptionDto = new ApplicationExceptionDto();
        applicationExceptionDto.setMessage(errorMessage.toString());
        return new ResponseEntity(applicationExceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ApplicationException.class, produces = "application/xml")
    public ResponseEntity handleApplicationException(ApplicationException e) {
        ApplicationExceptionDto applicationExceptionDto = new ApplicationExceptionDto();
        applicationExceptionDto.setMessage(e.getMessage());
        return switch (e.getType()) {
            case BAD_REQUEST -> new ResponseEntity(applicationExceptionDto, HttpStatus.BAD_REQUEST);
            case OBJECT_NOT_FOUND -> new ResponseEntity(applicationExceptionDto, HttpStatus.NOT_FOUND);
            case INTERNAL_SERVER_ERROR -> new ResponseEntity(applicationExceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

}
