package com.prueba.busqueda.infra.repository;

import com.prueba.busqueda.domain.BusquedaRepository;
import com.prueba.busqueda.domain.model.Busqueda;
import com.prueba.busqueda.shared.defaults.PruebaQuery;
import com.prueba.busqueda.shared.infra.repository.AbstractRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author fhidalgo
 */
@Service
public class BusquedaRepositoryImpl extends AbstractRepository implements BusquedaRepository {

    public BusquedaRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @PruebaQuery("SELECT NEXTVAL('secuencia_id_busqueda')")
    @Override
    public Long obtenerIdSecuencia() {
        return queryForNumber(Long.class);
    }

    @Override
    public void altaBusqueda(final Busqueda request) {
        altaNuevaBusqueda(request);
        altaEdadesBusqueda(request);
    }

    @PruebaQuery("INSERT INTO PUBLIC.BUSQUEDAS(SEARCH_ID, HOTEL_ID, CHECK_IN, CHECK_OUT) VALUES (?, ?, ?, ?)")
    private void altaNuevaBusqueda(final Busqueda request) {
        update(request.getSearchId(), request.getHotelId(), request.getCheckIn(), request.getCheckOut());
    }

    @PruebaQuery("INSERT INTO PUBLIC.BUSQUEDAS_EDADES(SEARCH_ID, AGE) VALUES (?, ?)")
    private void altaEdadesBusqueda(final Busqueda request) {
        request.getAges().stream().forEach(age -> update(request.getSearchId(), age));
    }

    @PruebaQuery(
        "SELECT B.SEARCH_ID, B.HOTEL_ID, B.CHECK_IN, B.CHECK_OUT, MAX(BE.ID_BUSQUEDAS_EDADES), ARRAY_TO_STRING(ARRAY_AGG(BE.AGE ORDER BY BE.AGE), ',') AS AGES_BD " + 
        "FROM PUBLIC.BUSQUEDAS B " + 
        "INNER JOIN PUBLIC.BUSQUEDAS_EDADES BE ON BE.SEARCH_ID = B.SEARCH_ID " + 
        "WHERE B.SEARCH_ID = ? " + 
        "GROUP BY B.SEARCH_ID")
    @Override
    public Busqueda obtenerDatosBusqueda(final Integer searchId) {
        return queryForObject(Busqueda.class, searchId);
    }

    @PruebaQuery("SELECT COUNT(SEARCH_ID) FROM PUBLIC.BUSQUEDAS WHERE HOTEL_ID = ? AND CHECK_IN = ? AND CHECK_OUT = ?")
    @Override
    public Integer contadorBusquedasSimilares(final Busqueda busqueda) {
        return queryForNumber(Integer.class, busqueda.getHotelId(), busqueda.getCheckIn(), busqueda.getCheckOut());
    }
    
    

}
