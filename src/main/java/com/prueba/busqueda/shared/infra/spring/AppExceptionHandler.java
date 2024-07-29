package com.prueba.busqueda.shared.infra.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.prueba.busqueda.domain.exc.ApiException;
import com.prueba.busqueda.shared.constantes.domain.model.ErrorApi;
import jakarta.validation.*;
import java.nio.file.AccessDeniedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.prueba.busqueda.shared.defaults.LogSupport;

import static com.prueba.busqueda.shared.constantes.Errores.*;


/**
 *
 * @author fhidalgo
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class AppExceptionHandler implements LogSupport {

    private static final String HA_OCURRIDO_UN_ERROR = "Ha ocurrido un error: {}";

    private final MessagesResourceService messagesResourceService;

    public AppExceptionHandler(MessagesResourceService messagesResourceService) {
        this.messagesResourceService = messagesResourceService;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<ErrorApi> handleEmptyResultDataAccessException(final EmptyResultDataAccessException exc) {
        logWarning(exc.getClass().getName(), exc.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorApi(messagesResourceService.getResourceString("exc.EmptyResultDataAccessException")));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorApi> handleIllegalArgumentException(final IllegalArgumentException exc) {
        logWarning(exc.getClass().getName(), exc.getMessage());

        if (exc.getMessage() != null) {
            return new ResponseEntity<>(new ErrorApi(exc.getMessage()), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new ErrorApi("Ha ocurrido un problema al realizar la operacion"), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorApi> handleAccessDeniedException(final AccessDeniedException exc) {
        logWarning(exc.getClass().getName(), exc.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorApi(messagesResourceService.getResourceString(exc.getMessage())));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<ErrorApi> handleMissingRequestHeaderException(final MissingRequestHeaderException exc) {
        logWarning(exc.getClass().getName(), exc.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorApi(messagesResourceService.getResourceString("exc.MissingRequestHeaderException")));
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ErrorApi> handleApiException(final ApiException exc) {
        logError(exc);
        return ResponseEntity.status(exc.getHttpSatus()).body(new ErrorApi(messagesResourceService.getResourceString(exc.getMessage())));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorApi> handleMissingServletRequestParameterException(final MissingServletRequestParameterException exc) {
        logWarning(HA_OCURRIDO_UN_ERROR, exc.getMessage());

        final ErrorApi apiError = new ErrorApi(messagesResourceService.getResourceString(ERROR_ENTRADA_NOVALIDO));

        apiError.addFieldError(exc.getParameterName(), messagesResourceService.getResourceString(ERROR_PARAMETRO_NOVALIDO));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorApi> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exc) {
        logWarning(HA_OCURRIDO_UN_ERROR, exc.getMessage());

        final ErrorApi apiError = new ErrorApi(messagesResourceService.getResourceString(ERROR_ENTRADA_NOVALIDO));

        exc.getBindingResult()
                .getFieldErrors()
                .stream().forEach(err -> apiError.addFieldError(err.getField(), messagesResourceService.getResourceString(err.getDefaultMessage())));

        exc.getBindingResult()
                .getGlobalErrors()
                .stream().forEach(err -> apiError.addError(err.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorApi> handleConstraintViolationException(final ConstraintViolationException exc) {
        logWarning(HA_OCURRIDO_UN_ERROR, exc.getMessage());

        final ErrorApi apiError = new ErrorApi(messagesResourceService.getResourceString(ERROR_ENTRADA_NOVALIDO));

        exc.getConstraintViolations().stream().forEach(
                violation -> apiError.addFieldError(violation.getPropertyPath().toString(), messagesResourceService.getResourceString(violation.getMessage()))
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(JsonMappingException.class)
    protected ResponseEntity<ErrorApi> handleJsonMappingException(final JsonMappingException exc) {
        logWarning("JsonMappingException: {}", exc.getMessage());

        final var apiError = new ErrorApi(messagesResourceService.getResourceString(ERROR_ENTRADA_NOVALIDO));

        apiError.addFieldError(
                exc.getPath().get(0).getFieldName(),
                messagesResourceService.getResourceString("error." + exc.getPath().get(0).getFieldName() + ".novalido"));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorApi> handleHttpMessageNotReadableException(final HttpMessageNotReadableException exc) {
        logWarning("Ha ocurrido un error al leer el json de entrada: {}, {}", exc.getCause().getClass().getName(), exc.getMessage());

        final var cause = exc.getCause();
        switch (cause) {
            case JsonMappingException iexc -> {
                return handleJsonMappingException(iexc);
            }
            case JsonProcessingException iexc -> {
                return handleException(iexc);
            }
            default -> {
                return handleException(exc);
            }
        }
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<ErrorApi> handleValidationException(final ValidationException exc) {
        logWarning(HA_OCURRIDO_UN_ERROR, exc.getMessage());

        switch (exc.getCause()) {
            case ApiException wsYaGanasteException -> {
                return ResponseEntity
                        .status(wsYaGanasteException.getHttpSatus())
                        .body(new ErrorApi(messagesResourceService.getResourceString(exc.getCause().getMessage())));
            }
            default -> {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorApi(messagesResourceService.getResourceString(ERROR_ENTRADA_NOVALIDO)));
            }
        }
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorApi> handleException(final Exception exc) {
        logError(exc, HA_OCURRIDO_UN_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorApi(messagesResourceService.getResourceString(ERROR_GENERAL)));
    }
}
