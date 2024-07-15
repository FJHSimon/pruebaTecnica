package com.prueba.busqueda.domain;

import com.prueba.busqueda.domain.model.AltaBusquedaRequest;
import com.prueba.busqueda.domain.model.AltaBusquedaResponse;
import com.prueba.busqueda.domain.model.ObtenerBusquedaResponse;

/**
 *
 * @author fhidalgo
 */
public interface BusquedaUseCases {
    
    AltaBusquedaResponse altaBusqueda(AltaBusquedaRequest request);
    ObtenerBusquedaResponse obtenerBusqueda(String searchId);
    
}
