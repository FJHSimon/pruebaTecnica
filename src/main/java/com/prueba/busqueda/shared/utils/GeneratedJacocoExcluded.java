package com.prueba.busqueda.shared.utils;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 *
 * @author fhidalgo
 */
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD, CONSTRUCTOR})
public @interface GeneratedJacocoExcluded {

    String value() default "";

}
