package com.prueba.busqueda.domain.exc;

import com.prueba.busqueda.shared.constantes.domain.model.ErrorApi;
import org.springframework.http.HttpStatus;

/**
 *
 * @author fhidalgo
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 245678944L;

    private final HttpStatus httpSatus;
    private final ErrorApi apiError;

    public ApiException(final HttpStatus httpSatus, final String message) {
        super(message);
        this.httpSatus = httpSatus;
        this.apiError = new ErrorApi(message);
    }

    public ApiException(final HttpStatus httpSatus, final String message, final Throwable cause) {
        super(message, cause);
        this.httpSatus = httpSatus;
        this.apiError = new ErrorApi(message);
    }

    public ApiException(final HttpStatus httpSatus, final ErrorApi apiError) {
        super(apiError.getMessage());
        this.httpSatus = httpSatus;
        this.apiError = apiError;
    }

    public ApiException(final HttpStatus httpSatus, final ErrorApi apiError, final Throwable cause) {
        super(apiError.getMessage(), cause);
        this.httpSatus = httpSatus;
        this.apiError = apiError;
    }

    public HttpStatus getHttpSatus() {
        return httpSatus;
    }

    public ErrorApi getErrorApi() {
        return apiError;
    }
}
