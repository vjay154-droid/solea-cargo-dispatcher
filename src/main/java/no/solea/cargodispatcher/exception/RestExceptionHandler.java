package no.solea.cargodispatcher.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import no.solea.cargodispatcher.dto.ApiErrorResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex,
                                                    HttpServletRequest request) {
        log.error("Runtime exception in {} and exception is {} ",
                request.getRequestURI(),ex.getMessage());
        ApiErrorResponseDTO apiError = new ApiErrorResponseDTO(
                    LocalDateTime.now(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ex.getMessage(),
                    request.getRequestURI()
                );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex,
                                                           HttpServletRequest request) {
        log.error("Service failed in {} and exception is {} ",
                request.getRequestURI(),ex.getMessage());
        ApiErrorResponseDTO apiError = new ApiErrorResponseDTO(
                LocalDateTime.now(),
                ex.getStatusCode().value(),
                ex.getReason(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiError, ex.getStatusCode());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("Validation failed in {} and exception is {} ",
                request.getContextPath(),ex.getMessage());
        ApiErrorResponseDTO apiError = new ApiErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                Objects.requireNonNull(
                        ex.getBindingResult().getFieldError()
                ).getDefaultMessage(),
                request.getContextPath()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex, HttpServletRequest request) {
        log.error("Uncaught exception in {} and exception is {} ",
                request.getRequestURI(),ex.getMessage());
        ApiErrorResponseDTO apiError = new ApiErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
