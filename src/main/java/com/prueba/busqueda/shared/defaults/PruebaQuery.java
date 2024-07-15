package com.prueba.busqueda.shared.defaults;

import com.prueba.busqueda.shared.utils.GeneratedJacocoExcluded;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author fhidalgo
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@GeneratedJacocoExcluded
public @interface PruebaQuery {
 
    String value() default "";
}
