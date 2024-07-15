package com.prueba.busqueda.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.busqueda.shared.defaults.LogSupport;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;


/**
 *
 * @author fhidalgo
 */
public class BusquedaDeserializer implements Deserializer<Busqueda>, LogSupport {
    
    private ObjectMapper objectMapper = new ObjectMapper();

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Busqueda deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                logWarning("Recibidos datos nulos durante la deserializacion");
                return null;
            }
            return getObjectMapper().readValue(new String(data, "UTF-8"), Busqueda.class);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new SerializationException("Ha ocurrido un error deserializando byte[] a Objeto");
        }
    }

    @Override
    public void close() {
    }

}
