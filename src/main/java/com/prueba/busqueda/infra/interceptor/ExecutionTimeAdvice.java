package com.prueba.busqueda.infra.interceptor;

import com.prueba.busqueda.shared.defaults.LogSupport;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Esta clase se encarga de generar un log con el tiempo de ejecucion para los
 * endpoints.
 *
 * @author fhidalgo
 */
@Aspect
@Component
public class ExecutionTimeAdvice implements LogSupport {

    //Se tiene que crear un metodo para cada anotacion ya que @around no se puede repetir.
    /**
     * Se ejecuta cuando se anota un metodo con @PostMapping.
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public Object executionPostMapping(final ProceedingJoinPoint point) throws Throwable {
        return execution(point);
    }

    /**
     * Se ejecuta cuando se anota un metodo con @GetMapping.
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Object executionGetMappingg(final ProceedingJoinPoint point) throws Throwable {
        return execution(point);
    }

    private Object execution(final ProceedingJoinPoint point) throws Throwable {
        final long startTime = System.currentTimeMillis();
        final Object object = point.proceed();
        final long endtime = System.currentTimeMillis();

        logInfo("TIEMPO DE OPERACION {}.{}: {} ms",
                point.getSignature().getDeclaringTypeName(),
                point.getSignature().getName(),
                endtime - startTime);

        return object;
    }

}
