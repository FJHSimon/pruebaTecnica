package com.prueba.busqueda.shared.constantes.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fhidalgo
 */
public class ErrorApi implements Serializable {

    private static final long serialVersionUID = 4564878941L;

    private String message;
    private List<String> errors;
    private List<ApiFieldError> fieldsErrors;

    public ErrorApi() {
        errors = new ArrayList<>();
        fieldsErrors = new ArrayList<>();
    }

    public ErrorApi(final String message) {
        this.message = message;
        errors = new ArrayList<>();
        fieldsErrors = new ArrayList<>();
    }

    public ErrorApi(
            final String message, 
            final List<String> errors, 
            final List<ApiFieldError> fieldsErrors) {
        this.message = message;
        this.errors = errors;
        this.fieldsErrors = fieldsErrors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(final List<String> errors) {
        this.errors = errors;
    }

    public List<ApiFieldError> getFieldsErrors() {
        return fieldsErrors;
    }

    public void setFieldsErrors(final List<ApiFieldError> fieldsErrors) {
        this.fieldsErrors = fieldsErrors;
    }

    public void addError(final String error) {
        errors.add(error);
    }

    public void addFieldError(final String field, final String error) {
        fieldsErrors.add(new ApiFieldError(field, error));
    }

    public static class ApiFieldError implements Serializable {

        private static final long serialVersionUID = 3154687787L;

        private String field;
        private String message;

        public ApiFieldError() {
        }
        
        public ApiFieldError(final String field, final String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(final String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(final String message) {
            this.message = message;
        }
    }

}
