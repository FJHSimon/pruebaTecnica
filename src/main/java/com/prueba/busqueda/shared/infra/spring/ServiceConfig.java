package com.prueba.busqueda.shared.infra.spring;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
 
/**
*
* @author fhidalgo
*/
@Configuration
@EnableScheduling
public class ServiceConfig {
 
    /**
     * Se cambia el nombre del bean, para poder colcoar varios servicios en el
     * mismo servidor web.
     *
     * @return
     */
    @Bean(name = "dsbusqueda")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
 
    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dsbusqueda") final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
 
    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .addModule(new JavaTimeModule())
                .build();
    }
}
