package com.prueba.busqueda.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author fhidalgo
 */
public class AltaBusquedaResponse {

    @Schema(description = "ID de busqueda dado de alta", name = "searchId", example="xxxxx")
    private String searchId;

    public AltaBusquedaResponse() {
        this.searchId = "-1";
    }
    
    public AltaBusquedaResponse(String searchId) {
        this.searchId = searchId;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(final String searchId) {
        this.searchId = searchId;
    }
}
