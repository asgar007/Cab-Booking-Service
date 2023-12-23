package com.assignment.cabservice.handler;

import com.assignment.cabservice.exception.DriverNotFoundException;
import com.assignment.cabservice.exception.RideAlreadyBookedException;
import com.assignment.cabservice.exception.RideHistoryNotFoundException;
import com.assignment.cabservice.exception.RideNotFoundException;
import com.assignment.cabservice.response.ErrorResponse;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@EnableWebMvc
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    // handleHttpMediaTypeNotSupported : triggers when the JSON is invalid
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        details.add(builder.toString());
        return ResponseEntity.ok(ErrorResponse.builder().error(true).data(details).build());
    }

    // handleHttpMessageNotReadable : triggers when the JSON is malformed
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable( HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.ok(ErrorResponse.builder().error(true).message(ex.getMessage()).build());
    }

    // handleMethodArgumentNotValid : triggers when @Valid fails
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.ok(ErrorResponse.builder().error(true)
                .data(ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> error.getField() + " : " + error.getDefaultMessage())
                        .collect(Collectors.toList()))
                .build());
    }

    // handleMissingServletRequestParameter : triggers when there are missing
    // parameters
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.ok(
                ErrorResponse.builder().error(true).message(ex.getParameterName() + " parameter is missing").build());
    }

    // handleMethodArgumentTypeMismatch : triggers when a parameter's type does not
    // match
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                             WebRequest request) {
        return ResponseEntity.ok(ErrorResponse.builder().error(true).message(ex.getMessage()).build());
    }

    // handleConstraintViolationException : triggers when @Validated fails
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> constraintViolationException(ConstraintViolationException exception,
                                                                      WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        if (!exception.getConstraintViolations().isEmpty()) {
            for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
                String fieldName = null;
                for (Path.Node node : constraintViolation.getPropertyPath()) {
                    fieldName = node.getName();
                }
                errors.put(fieldName, constraintViolation.getMessage());
            }
        }
        return ResponseEntity.ok(ErrorResponse.builder().error(true).data(errors).build());
    }

    // dataIntegrityViolationException : triggers when @Validated fails
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ErrorResponse> dataIntegrityViolationException(DataIntegrityViolationException exception,
                                                                         WebRequest request) {
        return ResponseEntity.ok(ErrorResponse.builder().error(true)
                .message(exception.getMostSpecificCause().getMessage().split("for")[0]).build());
    }

    // handleResourceNotFoundException : triggers when there is not resource with
    // the specified ID in BDD
    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ConfigDataResourceNotFoundException ex) {
        return ResponseEntity.ok(ErrorResponse.builder().error(true).message(ex.getMessage()).build());
    }

    // handleNoHandlerFoundException : triggers when the handler method is invalid
//    @Override
//    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
//                                                                   HttpStatus status, WebRequest request) {
//        return ResponseEntity.ok(ErrorResponse.builder().error(true).message(
//                        String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()))
//                .build());
//
//    }

    @ExceptionHandler({UserPrincipalNotFoundException.class})
    public ResponseEntity<ErrorResponse> userNotFoundException(UserPrincipalNotFoundException ex) {
        return ResponseEntity.ok(ErrorResponse.builder().error(true).message(ex.getMessage()).build());

    }

    @ExceptionHandler({DriverNotFoundException.class})
    public ResponseEntity<ErrorResponse> cardNotFoundExceptionException(DriverNotFoundException ex) {
        return ResponseEntity.ok(ErrorResponse.builder().error(true).message(ex.getMessage()).build());

    }
    @ExceptionHandler({RideHistoryNotFoundException.class})
    public ResponseEntity<ErrorResponse> rideHistoryNotFoundException(RideHistoryNotFoundException ex) {
        return ResponseEntity.ok(ErrorResponse.builder().error(true).message(ex.getMessage()).build());

    }

    @ExceptionHandler({RideNotFoundException.class})
    public ResponseEntity<ErrorResponse> rideNotFoundException(RideNotFoundException ex) {
        return ResponseEntity.ok(ErrorResponse.builder().error(true).message(ex.getMessage()).build());

    }
    @ExceptionHandler({RideAlreadyBookedException.class})
    public ResponseEntity<ErrorResponse> rideAlreadyBookedException(RideAlreadyBookedException ex) {
        return ResponseEntity.ok(ErrorResponse.builder().error(true).message(ex.getMessage()).build());

    }



    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleAll(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder().statusCode(""+HttpStatus.valueOf(400)).error(true).message(ex.getMessage()).build());

    }

}
