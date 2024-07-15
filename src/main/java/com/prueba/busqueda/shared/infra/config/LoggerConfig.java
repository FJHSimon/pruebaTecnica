package com.prueba.busqueda.shared.infra.config;

import com.prueba.busqueda.shared.defaults.LogSupport;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
 
/**
*
* @author fhidalgo
*/
@Service
@Validated
@ConfigurationProperties("com.busqueda.logger")
public class LoggerConfig implements LogSupport {
 
    @NotNull
    private List<String> listaNegraKeywords;
 
    @PostConstruct
    public void postConstruct() {
        logInfo("Lista Negra {}, {}", listaNegraKeywords, listaNegraKeywords.size());
    }

    public List<String> getListaNegraKeywords() {
        return listaNegraKeywords;
    }

    public void setListaNegraKeywords(List<String> listaNegraKeywords) {
        this.listaNegraKeywords = listaNegraKeywords;
    }
 
}
