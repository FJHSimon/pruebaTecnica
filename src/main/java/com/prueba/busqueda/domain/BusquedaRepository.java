package com.prueba.busqueda.domain;

import com.prueba.busqueda.domain.model.Busqueda;

/**
 *
 * @author fhidalgo
 */
public interface BusquedaRepository {
    
    Long obtenerIdSecuencia();
    void altaBusqueda(Busqueda request);
    Busqueda obtenerDatosBusqueda(Integer searchId);
    Integer contadorBusquedasSimilares(Busqueda busqueda);
}
