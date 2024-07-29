package com.prueba.busqueda.shared.infra.spring.kafka;

import com.prueba.busqueda.domain.BusquedaRepository;
import com.prueba.busqueda.domain.model.Busqueda;
import com.prueba.busqueda.shared.defaults.LogSupport;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
 
import static com.prueba.busqueda.shared.constantes.Constantes.*;


/**
 *
 * @author fhidalgo
 */
@Component
public class KafkaBusquedaConsumer implements LogSupport {
    
    public final BusquedaRepository busquedaRepository;

    public KafkaBusquedaConsumer(final BusquedaRepository busquedaRepository) {
        this.busquedaRepository = busquedaRepository;
    }
 
    @KafkaListener(
            topics = KAFKA_TOPIC, 
            groupId = KAFKA_GROUP, 
            containerFactory = "listenerContainerFactory")
    public void listen(@Payload Busqueda message) {
        logInfo("Objeto de busqueda recibido en consumer: {} {}", message.getSearchId(), message.toString());
        busquedaRepository.altaBusqueda(message);
    }
 
}
