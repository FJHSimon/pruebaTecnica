package com.prueba.busqueda.infra.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.busqueda.domain.BusquedaUseCases;
import com.prueba.busqueda.domain.model.AltaBusquedaRequest;
import com.prueba.busqueda.domain.model.AltaBusquedaResponse;
import com.prueba.busqueda.domain.model.Busqueda;
import com.prueba.busqueda.domain.model.ObtenerBusquedaResponse;
import com.prueba.busqueda.shared.constantes.domain.model.ErrorApi;
import com.prueba.busqueda.shared.infra.spring.MessagesResourceService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 *
 * @author fhidalgo
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import({MessagesResourceService.class})
class TestBusquedaEndpoint {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BusquedaUseCases busquedaUseCases;
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    private static String fechaAnteriorAHoy;
    private static String fechaPosteriorAHoyUnDia;
    private static String fechaPosteriorAHoyDosDias;
    
    @BeforeAll
    public static void prepararDatosPrueba() {
        fechaAnteriorAHoy = LocalDate.now().minusDays(1).format(FORMATTER);
        fechaPosteriorAHoyUnDia = LocalDate.now().plusDays(1).format(FORMATTER);
        fechaPosteriorAHoyDosDias = LocalDate.now().plusDays(2).format(FORMATTER);
    }

    /**
     * Test de flujo correcto de alta de busqueda, emulando el resultado
     * @throws Exception 
     */
    @Test
    void altaBusqueda_ok() throws Exception {

        final List<Integer> lista = new LinkedList<>();
        lista.add(1);

        final var request = new AltaBusquedaRequest.Builder()
                .hotelId("12345")
                .checkIn(fechaPosteriorAHoyUnDia)
                .checkOut(fechaPosteriorAHoyDosDias)
                .ages(lista)
                .build();

        doReturn(new AltaBusquedaResponse("1223")).when(busquedaUseCases).altaBusqueda(request);

        final MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final var respuesta = objectMapper.readValue(result.getResponse().getContentAsString(), AltaBusquedaResponse.class);

        assertThat(respuesta, isA(AltaBusquedaResponse.class));
    }

    public static Stream<Arguments> altaBusquedaCamposRequiredTestMethodSourceArgs() {
        final List<Integer> lista = new LinkedList<>();
        lista.add(1);
        
        return Stream.of(
            Arguments.of("hotelId", "El ID de hotel no puede ser nulo", new AltaBusquedaRequest.Builder().checkIn(fechaPosteriorAHoyUnDia).checkOut(fechaPosteriorAHoyDosDias).ages(lista).build()),
            Arguments.of("checkIn", "La fecha de checkIn no puede estar vacia", new AltaBusquedaRequest.Builder().hotelId("123456").checkOut(fechaPosteriorAHoyDosDias).ages(lista).build()),
            Arguments.of("checkOut", "La fecha de checkOut no puede estar vacia", new AltaBusquedaRequest.Builder().hotelId("123456").checkIn(fechaPosteriorAHoyUnDia).ages(lista).build()),
            Arguments.of("ages", "La lista de edades no puede ser nula", new AltaBusquedaRequest.Builder().hotelId("123456").checkIn(fechaPosteriorAHoyUnDia).checkOut(fechaPosteriorAHoyDosDias).build())
        );
    }

    /**
     * Test de errores de validacion de objeto nulo en los distintos campos de la peticion,
     * con argumentos obtenidos a partir de metodo anterior
     * @throws Exception 
     */
    @ParameterizedTest
    @MethodSource("altaBusquedaCamposRequiredTestMethodSourceArgs")
    void altaBusqueda_errorCamposRequiredBadRequest(final String objetoError, final String mensajeError, final AltaBusquedaRequest request) throws Exception {

        doReturn(new AltaBusquedaResponse("1223")).when(busquedaUseCases).altaBusqueda(request);

        final MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();

        final var respuesta = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorApi.class);

        assertEquals(respuesta.getFieldsErrors().size(), 1);
        assertEquals(respuesta.getFieldsErrors().get(0).getField(), objetoError);
        assertEquals(respuesta.getFieldsErrors().get(0).getMessage(), mensajeError);
    }

    public static Stream<Arguments> altaBusquedaValidarFechasMethodSourceArgs() {
        final List<Integer> lista = new LinkedList<>();
        lista.add(1);
        
        return Stream.of(
            Arguments.of("La fecha de entrada no es valida", new AltaBusquedaRequest.Builder().hotelId("123456").checkIn("1234").checkOut(fechaPosteriorAHoyDosDias).ages(lista).build()),
            Arguments.of("La fecha de salida no es valida", new AltaBusquedaRequest.Builder().hotelId("123456").checkIn(fechaPosteriorAHoyUnDia).checkOut("1234").ages(lista).build()),
            Arguments.of("La fecha de entrada no puede ser anterior al dia de hoy", new AltaBusquedaRequest.Builder().hotelId("123456").checkIn(fechaAnteriorAHoy).checkOut(fechaPosteriorAHoyUnDia).ages(lista).build()),
            Arguments.of("La fecha de salida no puede ser anterior al dia de hoy", new AltaBusquedaRequest.Builder().hotelId("123456").checkIn(fechaPosteriorAHoyUnDia).checkOut(fechaAnteriorAHoy).ages(lista).build()),
            Arguments.of("La fecha de salida no puede ser anterior a la fecha de entrada", new AltaBusquedaRequest.Builder().hotelId("123456").checkIn(fechaPosteriorAHoyDosDias).checkOut(fechaPosteriorAHoyUnDia).ages(lista).build())
        );
    }

    /**
     * Test de distintos errores de validacion de fecha en peticion de alta de busqueda,
     * con argumentos obtenidos a partir de metodo anterior
     * @throws Exception 
     */
    @ParameterizedTest
    @MethodSource("altaBusquedaValidarFechasMethodSourceArgs")
    void altaBusqueda_errorFechasBadRequest(final String mensajeError, final AltaBusquedaRequest request) throws Exception {

        doReturn(new AltaBusquedaResponse("1223")).when(busquedaUseCases).altaBusqueda(request);

        final MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();

        final var respuesta = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorApi.class);

        assertEquals(respuesta.getFieldsErrors().size(), 0);
        assertEquals(respuesta.getMessage(), mensajeError);
    }
    
    /**
     * Test de flujo correcto de busqueda de searchId, emulando objeto de respuesta
     * @throws Exception 
     */
    @Test
    void contadorBusqueda_ok() throws Exception {
        final String request = "12";
        
        final Busqueda busqueda = new Busqueda("1234aBc", "08/07/2024", "11/07/2024", Arrays.asList(1, 3, 29, 30));
        doReturn(new ObtenerBusquedaResponse("12", busqueda, 6)).when(busquedaUseCases).obtenerBusqueda(request);

        final MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/count/12")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final var respuesta = objectMapper.readValue(result.getResponse().getContentAsString(), ObtenerBusquedaResponse.class);

        assertThat(respuesta, isA(ObtenerBusquedaResponse.class));
    }
    
    /**
     * Test de error de validacion de searchId introducido no numerico
     * @throws Exception 
     */
    @Test
    void contadorBusqueda_errorSearchIdValidatorBadRequest() throws Exception {

        doReturn(new ObtenerBusquedaResponse("0", new Busqueda(), 1)).when(busquedaUseCases).obtenerBusqueda("abc");

        final String requestPath = "/count/" + "abc";
        
        final MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(requestPath)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();

        final var respuesta = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorApi.class);

        assertEquals(respuesta.getFieldsErrors().size(), 1);
        assertEquals(respuesta.getFieldsErrors().get(0).getField(), "contadorBusqueda.searchId");
        assertEquals(respuesta.getFieldsErrors().get(0).getMessage(), "El id de busqueda debe ser numerico");
    }
}
