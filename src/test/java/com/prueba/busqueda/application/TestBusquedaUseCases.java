package com.prueba.busqueda.application;

import com.prueba.busqueda.domain.BusquedaUseCases;
import com.prueba.busqueda.domain.model.AltaBusquedaRequest;
import com.prueba.busqueda.domain.model.AltaBusquedaResponse;
import com.prueba.busqueda.domain.model.ObtenerBusquedaResponse;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 *
 * @author fhidalgo
 */
@SpringBootTest
public class TestBusquedaUseCases {
    
    @Autowired
    private BusquedaUseCases busquedaUseCases;

    @Test
    void testAltaBusqueda_ok() {
        final List<Integer> lista = new LinkedList<>();
        lista.add(1);

        AltaBusquedaRequest altaBusquedaRequest = new AltaBusquedaRequest("12345", "10/07/2024", "20/07/2024", lista);

        assertThat(busquedaUseCases.altaBusqueda(altaBusquedaRequest), isA(AltaBusquedaResponse.class));
    }
    
    @Test
    void testObtenerBusqueda_ok() {
        final String searchId = "12";
        
        assertThat(busquedaUseCases.obtenerBusqueda(searchId), isA(ObtenerBusquedaResponse.class));
    }
    
    @Test
    void testObtenerBusqueda_errorSearchIdNoExiste() {

        final String searchId = "123";
        assertThrows(EmptyResultDataAccessException.class, () -> busquedaUseCases.obtenerBusqueda(searchId));
    }

}
