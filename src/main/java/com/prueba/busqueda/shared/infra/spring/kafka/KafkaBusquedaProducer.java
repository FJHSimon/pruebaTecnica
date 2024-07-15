package com.prueba.busqueda.shared.infra.spring.kafka;

import com.prueba.busqueda.domain.model.Busqueda;
import com.prueba.busqueda.shared.defaults.LogSupport;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
 
import static com.prueba.busqueda.shared.constantes.Constantes.KAFKA_TOPIC;
import java.util.concurrent.ExecutionException;


/**
 *
 * @author fhidalgo
 */
@Component
public class KafkaBusquedaProducer implements LogSupport {
 
    private final KafkaTemplate<String, Busqueda> kafkaTemplate;

    public KafkaBusquedaProducer(final KafkaTemplate<String, Busqueda> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
 
    public Long enviar(final Busqueda busqueda) throws InterruptedException, ExecutionException {
 
        logInfo("Enviando elemento: {} al broker de KAFKA. Topic: {}", busqueda.toString(), KAFKA_TOPIC);
        final SendResult<String, Busqueda> completableFuture = kafkaTemplate.send(KAFKA_TOPIC, busqueda).get();
        return completableFuture.getRecordMetadata().offset();
    }
 
}
