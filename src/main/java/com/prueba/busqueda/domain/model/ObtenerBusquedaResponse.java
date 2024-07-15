package com.prueba.busqueda.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author fhidalgo
 */
public class ObtenerBusquedaResponse {

    @Schema(description = "ID de busqueda dado de alta", name = "searchId", example="xxxxx")
    private String searchId;
    @Schema(description = "Objeto busqueda encontrado", name = "search", type="object")
    private Busqueda search;
    @Schema(description = "Numero de busquedas similares", name = "count", example="0")
    private Integer count;

    public ObtenerBusquedaResponse() {
        this.searchId = "-1";
        this.search = new Busqueda();
        this.count = 0;
    }
            
    public ObtenerBusquedaResponse(
            final String searchId, 
            final Busqueda search, 
            final Integer count) {
        
        this.searchId = searchId;
        this.search = search;
        this.count = count;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(final String searchId) {
        this.searchId = searchId;
    }

    public Busqueda getSearch() {
        return search;
    }

    public void setSearch(final Busqueda search) {
        this.search = search;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }
}
