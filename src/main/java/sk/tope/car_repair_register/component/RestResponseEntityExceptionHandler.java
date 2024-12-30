package sk.tope.car_repair_register.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sk.tope.car_repair_register.api.service.so.ErrorMessageSo;
import sk.tope.car_repair_register.api.service.so.FieldErrorSo;
import sk.tope.car_repair_register.bundle.ErrorBundle;

import java.util.Comparator;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        LOGGER.error("RestResponseEntityExceptionHandler - handleMethodArgumentNotValid({},{},{},{})", ex, headers, status, request);
        List<FieldErrorSo> errors = ex.getBindingResult().getFieldErrors().stream()
                .sorted(Comparator.comparing(org.springframework.validation.FieldError::getField))
                .map(error -> new FieldErrorSo(error.getField(), error.getDefaultMessage()))
                .toList();

        return new ResponseEntity<>(new ErrorMessageSo(HttpStatus.valueOf(status.value()), ErrorBundle.VALIDATION_ERROR.name(), errors), status);
    }

    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        LOGGER.error("RestResponseEntityExceptionHandler - handleResponseStatusException({},{})", ex, request);
        return new ResponseEntity<>(new ErrorMessageSo(HttpStatus.valueOf(ex.getStatusCode().value()), ex.getReason(), null), ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        LOGGER.error("RestResponseEntityExceptionHandler - handleGlobalException({},{})", ex, request);
        return new ResponseEntity<>(new ErrorMessageSo(HttpStatus.INTERNAL_SERVER_ERROR, ErrorBundle.UNEXPECTED_ERROR.name(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
