## Sobre el proyecto
<h3>Aplicacion Spring para operaciones de busqueda y retorno de datos de busqueda de hoteles</h3>

<p>-Una vez desplegado se accede a traves de la URL: <a href=#>http://localhost:9200/busqueda/swagger-ui/index.html</a></p>


### Construido con

Principales frameworks/librerias utilizados:

* Spring Boot Framework
* Maven
* Apache Kafka
* PostgreSQL
* JUnit 5
* H2 DB
* Jacoco Coverage


### Prerequisitos

* KAFKA

La aplicacion se conecta a un broker de Kafka configurado en el fichero application.yml:

spring:
	kafka:
		bootstrap-servers: localhost:9092
		
* POSTGRESQL

El consumer de Kafka persiste los datos en una base de datos PostgreSQL, cuya conexión también se configura en el fichero application.yml:

spring:
	datasource:        
        driver-class-name: org.postgresql.Driver
        jdbc-url: jdbc:postgresql://localhost:5439/postgres
        username: postgres
        password: 
        minimumIdle: 10
        maximumPoolSize: 100
        maxLifetime: 3600000   

Creación de schema y tablas en Base de datos PostgreSQL:

CREATE SCHEMA IF NOT EXISTS "PUBLIC";

CREATE SEQUENCE secuencia_id_busqueda;

CREATE TABLE IF NOT EXISTS "PUBLIC"."BUSQUEDAS"
    (
        SEARCH_ID BIGINT NOT NULL,
        HOTEL_ID VARCHAR(20) NOT NULL,
        CHECK_IN VARCHAR(20) NOT NULL,
        CHECK_OUT VARCHAR(20) NOT NULL,
        CONSTRAINT BUSQUEDAS_PK PRIMARY KEY (SEARCH_ID)
    );

CREATE TABLE IF NOT EXISTS "PUBLIC"."BUSQUEDAS_EDADES"
    (
        ID_BUSQUEDAS_EDADES INTEGER GENERATED ALWAYS AS IDENTITY,
        SEARCH_ID INTEGER,
        AGE INTEGER,
        CONSTRAINT BUSQUEDAS_EDADES_pk PRIMARY KEY (ID_BUSQUEDAS_EDADES),
        CONSTRAINT BUSQUEDASEDADES_FK1 FOREIGN KEY (SEARCH_ID) REFERENCES "PUBLIC"."BUSQUEDAS"(SEARCH_ID)
    );
