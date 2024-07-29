package com.prueba.busqueda.infra.endpoint;

import com.prueba.busqueda.domain.BusquedaUseCases;
import com.prueba.busqueda.domain.model.*;
import com.prueba.busqueda.infra.endpoint.validation.ValidarRangoFechas;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.media.Schema;
import com.prueba.busqueda.shared.constantes.domain.model.ErrorApi;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.constraints.*;

import static org.springframework.http.MediaType.*;
import static com.prueba.busqueda.shared.constantes.HttpMethods.*;
import static com.prueba.busqueda.shared.constantes.ResponseCodes.*;
import static com.prueba.busqueda.shared.constantes.Errores.*;


/**
 *
 * @author fhidalgo
 */
@Tag(name = "Busqueda", description = "Prueba Tecnica Avoristech")
@RestController
@Validated
public class BusquedaEndpoint {

    private final BusquedaUseCases busquedaUseCases;

    public BusquedaEndpoint(final BusquedaUseCases busquedaUseCases) {
        this.busquedaUseCases = busquedaUseCases;
    }
    
    @Operation(
            method = POST_METHOD,
            description = "Da de alta una busqueda en el sistema"
    )
    @ApiResponse(
            responseCode = HTTP_CODE_OK, 
            description = "Alta de busqueda en sistema", 
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = AltaBusquedaResponse.class)))
    @ApiResponse(
            responseCode = HTTP_CODE_BAD_REQUEST, 
            description = "Parametros no validos", 
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorApi.class)))
    @ApiResponse(
            responseCode = HTTP_CODE_UNPROCESSABLE, 
            description = "Reglas no permitidas", 
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorApi.class)))
    @PostMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AltaBusquedaResponse> altaBusqueda(
            final HttpServletRequest request,
            @Valid 
            @RequestBody 
            @ValidarRangoFechas
            final AltaBusquedaRequest altaBusquedaRequest) {
        
        final AltaBusquedaResponse response = busquedaUseCases.altaBusqueda(altaBusquedaRequest);
        
        return ResponseEntity.ok(response);
    }
    
    
    @Operation(
            method = GET_METHOD,
            description = "Da de alta una busqueda en el sistema"
    )
    @ApiResponse(
            responseCode = HTTP_CODE_OK, 
            description = "Alta de busqueda en sistema", 
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = AltaBusquedaResponse.class)))
    @ApiResponse(
            responseCode = HTTP_CODE_BAD_REQUEST, 
            description = "Parametros no validos", 
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorApi.class)))
    @ApiResponse(
            responseCode = HTTP_CODE_UNPROCESSABLE, 
            description = "Reglas no permitidas", 
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorApi.class)))
    @GetMapping(value = "/count/{searchId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ObtenerBusquedaResponse> contadorBusqueda(
            final HttpServletRequest request,
            @Parameter(name = "searchId", description = "Identificador de la busqueda", required = true, example = "2")
            @NotEmpty(message = ERROR_SEARCH_ID_REQUIRED)
            @Pattern(regexp = "^-?\\d*\\.?\\d*", message = ERROR_SEARCH_ID_NUMERICO)
            @PathVariable final String searchId) {

        return ResponseEntity.ok(busquedaUseCases.obtenerBusqueda(searchId));
    }
}
