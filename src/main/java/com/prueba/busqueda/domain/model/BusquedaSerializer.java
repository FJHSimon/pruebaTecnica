package com.prueba.busqueda.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.busqueda.shared.defaults.LogSupport;
import com.prueba.busqueda.shared.utils.GeneratedJacocoExcluded;
import java.util.Map;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;


/**
 *
 * @author fhidalgo
 */
public class BusquedaSerializer implements Serializer<Busqueda>, LogSupport {
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Busqueda data) {
        try {
            if (data == null){
                logWarning("Recibidos datos nulos durante la serializacion");
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Ha ocurrido un error serializando el objeto a byte[]");
        }
    }

    @Override
    @GeneratedJacocoExcluded
    public void close() {
    }

}
