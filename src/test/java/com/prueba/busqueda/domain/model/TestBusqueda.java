package com.prueba.busqueda.domain.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author fhidalgo
 */
@SpringBootTest
public class TestBusqueda {

    @Test
    void agesToListTest_ok() {
        final String agesBd = "3,9,25,44";
        final List<Integer> listaEdades = Arrays.asList(9, 25, 3, 44);
        
        final Busqueda busquedaTest = new Busqueda("123abc", "01/01/1900", "01/01/1900", listaEdades);
        final List<Integer> lista = busquedaTest.agesToList(agesBd);
        
        Assertions.assertEquals(new HashSet<>(listaEdades), new HashSet<>(lista));
    }
    
}
