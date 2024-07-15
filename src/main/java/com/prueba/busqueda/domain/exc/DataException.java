package com.prueba.busqueda.domain.exc;

import com.prueba.busqueda.shared.constantes.domain.model.ErrorApi;
import org.springframework.http.HttpStatus;

/**
 *
 * @author fhidalgo
 */
public class DataException extends ApiException {

    private static final long serialVersionUID = 1345678964L;

    public DataException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public DataException(final String message, final Throwable cause) {
        super(HttpStatus.BAD_REQUEST, message, cause);
    }

    public DataException(final ErrorApi apiError) {
        super(HttpStatus.BAD_REQUEST, apiError);
    }

    public DataException(final ErrorApi apiError, final Throwable cause) {
        super(HttpStatus.BAD_REQUEST, apiError, cause);
    }

}
