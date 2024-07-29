package com.prueba.busqueda.shared.constantes;

import com.prueba.busqueda.shared.utils.GeneratedJacocoExcluded;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author fhidalgo
 */
@GeneratedJacocoExcluded("no incluido en los test")
public final class Constantes {

    public static final short BD_TRUE = 1;
    public static final short BD_FALSE = 0;
    public static final String LOGGER_CONFIG = "";
    public static final String KAFKA_TOPIC = "hotel_availability_searches";
    public static final String KAFKA_GROUP = "hotel_availability_searches_group";
    public static final DateTimeFormatter FORMATO_FECHA_VALIDATOR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    

    private Constantes() {
        //Clase de constantes no instanciable
    }

}
