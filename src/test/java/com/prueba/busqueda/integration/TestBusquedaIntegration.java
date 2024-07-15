package com.prueba.busqueda.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.busqueda.domain.model.AltaBusquedaRequest;
import com.prueba.busqueda.domain.model.AltaBusquedaResponse;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 *
 * @author fhidalgo
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestBusquedaIntegration {

    @LocalServerPort
    private int port;
    
    @Value("${server.servlet.context-path}")
    private String contextPath;
    
    @Autowired
    private TestRestTemplate testRestTemplate;

    private static HttpHeaders headers;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private String crearURL() {
        return "http://localhost:" + port + contextPath;
    }
    
    @Test
    void testAltaBusqueda_ok() throws Exception {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        String peticion = objectMapper.writeValueAsString(new AltaBusquedaRequest.Builder()
                .hotelId("123abc")
                .checkIn("20/08/2024")
                .checkOut("25/08/2024")
                .ages(Arrays.asList(4, 7, 23, 45))
                .build());
        
        HttpEntity<String> entity = new HttpEntity<>(peticion, headers);
        ResponseEntity<AltaBusquedaResponse> response = testRestTemplate.exchange(
            crearURL() + "/search", HttpMethod.POST, entity, AltaBusquedaResponse.class);
        
        AltaBusquedaResponse altaBusquedaResponse = response.getBody();
        
        assert altaBusquedaResponse != null;
        
        assertEquals(HttpStatus.OK,response.getStatusCode());
        
        
    }
    
    
}
