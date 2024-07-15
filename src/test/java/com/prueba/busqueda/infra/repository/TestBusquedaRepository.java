package com.prueba.busqueda.infra.repository;

import com.prueba.busqueda.domain.BusquedaRepository;
import com.prueba.busqueda.domain.model.Busqueda;
import java.util.LinkedList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


/**
 *
 * @author fhidalgo
 */
@JdbcTest
@Sql(scripts = {"/schemaBusqueda.sql", "/dataBusqueda.sql"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // Se recrea la BD en cada test
@Import({BusquedaRepositoryImpl.class})
public class TestBusquedaRepository {
 
    @Autowired
    private BusquedaRepository busquedaRepository;
    
    @Test
    void testObtenerIdSecuencia() {
        assertThat(busquedaRepository.obtenerIdSecuencia(), isA(Long.class));
    }
    
    @Test
    void testAltaBusqueda() {
        final List<Integer> lista = new LinkedList<>();
        lista.add(1);
        
        assertDoesNotThrow((Executable) () -> busquedaRepository.altaBusqueda(new Busqueda(11L, "12345", "10/07/2024", "20/07/2024", lista)));
    }
    
    @Test
    void testobtenerDatosBusqueda() {
        assertThat(busquedaRepository.obtenerDatosBusqueda(10), isA(Busqueda.class));
    }
  
}
