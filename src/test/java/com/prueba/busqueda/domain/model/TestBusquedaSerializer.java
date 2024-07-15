package com.prueba.busqueda.domain.model;

import org.apache.kafka.common.errors.SerializationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author fhidalgo
 */
@SpringBootTest
public class TestBusquedaSerializer {

    @Test
    void busquedaSerializar_errorSerializarBusquedaNulo() {
        Assertions.assertNull(new BusquedaSerializer().serialize("topic", null));
    }

    @Test
    void busquedaSerializar_errorSerializarBusquedaExcepcion() {
        final BusquedaSerializer serializer = mock(BusquedaSerializer.class);
        final Busqueda busquedaTest = mock(Busqueda.class);
        when(serializer.serialize("topic", busquedaTest)).thenThrow(SerializationException.class);
        
        Assertions.assertThrows(SerializationException.class, () -> serializer.serialize("topic", busquedaTest));
    }
}
