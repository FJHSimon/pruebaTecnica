package com.prueba.busqueda.application;

import com.prueba.busqueda.domain.BusquedaRepository;
import com.prueba.busqueda.domain.BusquedaUseCases;
import com.prueba.busqueda.domain.exc.ApiException;
import com.prueba.busqueda.domain.model.AltaBusquedaRequest;
import com.prueba.busqueda.domain.model.AltaBusquedaResponse;
import com.prueba.busqueda.domain.model.Busqueda;
import com.prueba.busqueda.domain.model.ObtenerBusquedaResponse;
import com.prueba.busqueda.shared.defaults.LogSupport;
import com.prueba.busqueda.shared.infra.spring.kafka.KafkaBusquedaProducer;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author fhidalgo
 */
@Service
public class BusquedaUseCasesImpl implements BusquedaUseCases, LogSupport {

    @Autowired
    public BusquedaRepository busquedaRepository;

    @Autowired
    public KafkaBusquedaProducer kafkaBusquedaProducer;

    @Override
    public AltaBusquedaResponse altaBusqueda(final AltaBusquedaRequest request) {
        final Busqueda busqueda= request.toBusquedaObject();
        final Long searchId = busquedaRepository.obtenerIdSecuencia();
        busqueda.setSearchId(searchId);
        final AltaBusquedaResponse response = new AltaBusquedaResponse(String.valueOf(searchId));
        try {
            kafkaBusquedaProducer.enviar(busqueda);
        } catch (InterruptedException | ExecutionException ex) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Ha habido un error al enviar");
        }
        return response;
    }

    @Override
    public ObtenerBusquedaResponse obtenerBusqueda(String searchId) {
        final ObtenerBusquedaResponse response = new ObtenerBusquedaResponse();
        final Busqueda busquedaEspecifica = busquedaRepository.obtenerDatosBusqueda(Integer.valueOf(searchId));
        final Integer contador = busquedaRepository.contadorBusquedasSimilares(busquedaEspecifica);
        busquedaEspecifica.setAges(busquedaEspecifica.agesToList(busquedaEspecifica.getAgesBd()));
        
        response.setSearchId(searchId);
        response.setSearch(busquedaEspecifica);
        response.setCount(contador);
        
        return response;
    }

}
