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
public class TestBusquedaDeserializer {

    @Test
    void busquedaSerializar_errorDeserializarBusquedaNulo() {
        Assertions.assertNull(new BusquedaDeserializer().deserialize("topic", null));
    }

    @Test
    void busquedaSerializar_errorSerializarBusquedaExcepcion() {
        final BusquedaDeserializer deserializer = mock(BusquedaDeserializer.class);
        final byte[] data = new byte[1];
        when(deserializer.deserialize("topic", data)).thenThrow(SerializationException.class);
        
        Assertions.assertThrows(SerializationException.class, () -> deserializer.deserialize("topic", data));
    }
}
