package com.prueba.busqueda.infra.endpoint.validation;

import com.prueba.busqueda.domain.exc.DataException;
import com.prueba.busqueda.domain.model.AltaBusquedaRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import com.prueba.busqueda.infra.endpoint.validation.ValidarRangoFechas.RangoFechasValidator;

import static java.lang.annotation.ElementType.*;
import static com.prueba.busqueda.shared.constantes.Errores.*;


/**
 *
 * @author fhidalgo
 */
@Documented
@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RangoFechasValidator.class)
public @interface ValidarRangoFechas {

    String message() default "";
 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
    @SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
    class RangoFechasValidator implements ConstraintValidator<ValidarRangoFechas, AltaBusquedaRequest> {
 
        @Override
        public boolean isValid(final AltaBusquedaRequest value, final ConstraintValidatorContext context) {
            LocalDate fechaCheckIn;
            LocalDate fechaCheckOut;
            
            if (value != null) {
                try {
                    fechaCheckIn = LocalDate.parse(value.getCheckIn(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (DateTimeParseException e) {
                    throw new DataException(ERROR_FECHA_CHECKIN_NOVALIDA);
                }
                
                try {
                    fechaCheckOut = LocalDate.parse(value.getCheckOut(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (DateTimeParseException e) {
                    throw new DataException(ERROR_FECHA_CHECKOUT_NOVALIDA);
                }
                
                if (fechaCheckIn.isBefore(LocalDate.now())) {
                    throw new DataException(ERROR_FECHA_CHECKIN_ANTERIOR_HOY);
                }
                if (fechaCheckOut.isBefore(LocalDate.now())) {
                    throw new DataException(ERROR_FECHA_CHECKOUT_ANTERIOR_HOY);
                }
                
                if(fechaCheckOut.isBefore(fechaCheckIn)) {
                    throw new DataException(ERROR_RANGO_FECHAS_INVALIDO);
                }
            }
            return true;
        }
    }
}
