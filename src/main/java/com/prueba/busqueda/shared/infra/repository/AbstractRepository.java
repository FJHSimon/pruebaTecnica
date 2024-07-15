package com.prueba.busqueda.shared.infra.repository;

import com.prueba.busqueda.shared.defaults.LogSupport;
import java.lang.reflect.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.util.ClassUtils;
import com.prueba.busqueda.shared.defaults.PruebaQuery;
 
import static com.prueba.busqueda.shared.constantes.Constantes.LOGGER_CONFIG;
 

/**
*
* @author fhidalgo
*/
public abstract class AbstractRepository implements LogSupport { //NOSONAR
 
    protected final JdbcTemplate jdbcTemplate;
    protected final NamedParameterJdbcTemplate namedJdbc;
 
    @Value("${" + LOGGER_CONFIG + ".logSqlKeywords:false}")
    private Boolean logSqlKeywords;
 
    @Value("${" + LOGGER_CONFIG + ".keywords:}#{T(java.util.Collections).emptyList()}")
    private List<String> keywords;
 
    protected AbstractRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbc = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()); //NOSONAR el datasource no es nulo
    }
 
    private Method getCurrentMethod() {
        return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                .walk(s -> s.limit(10).map(this::methodAnnotationPruebaQuery).filter(Objects::nonNull)
                .findFirst().get());
    }
 
    private Method methodAnnotationPruebaQuery(final StackWalker.StackFrame sf) {
        for (final Method method : sf.getDeclaringClass().getDeclaredMethods()) {
            if (method.getName().equals(sf.getMethodName()) && method.isAnnotationPresent(PruebaQuery.class)) {
                return method;
            }
        }
        return null;
    }
 
    protected final <T extends Object> T queryForObject(final Class<T> clase, final Object... params) {
        final String query = getCurrentMethod().getAnnotation(PruebaQuery.class).value();
        if (isQueryLoggeable(query)) {
            logSqlQueryParams(query, params);
        }
 
        return jdbcTemplate.queryForObject(
                query,
                BeanPropertyRowMapper.newInstance(clase),
                params);
    }
 
    protected final <T extends Number> T queryForNumber(final Class<T> clase, final Object... params) {
        final String query = getCurrentMethod().getAnnotation(PruebaQuery.class).value();
        if (isQueryLoggeable(query)) {
            logSqlQueryParams(query, params);
        }
 
        return jdbcTemplate.queryForObject(
                query,
                clase,
                params);
    }
 
    protected final <T extends String> T queryForString(final Class<T> clase, final Object... params) {
        final String query = getCurrentMethod().getAnnotation(PruebaQuery.class).value();
        if (isQueryLoggeable(query)) {
            logSqlQueryParams(query, params);
        }
 
        try {
            return jdbcTemplate.queryForObject(
                    query,
                    clase,
                    params);
        } catch (EmptyResultDataAccessException exc) {
            return null;
        }
    }
 
    protected final boolean exists(final Object... params) {
        final String query = getCurrentMethod().getAnnotation(PruebaQuery.class).value();
        if (isQueryLoggeable(query)) {
            logSqlQueryParams(query, params);
        }
 
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    query,
                    Boolean.class,
                    params)).orElse(false);
        } catch (EmptyResultDataAccessException exc) {
            return false;
        }
    }
    protected final <T extends Object> T queryForObjectNamed(final Class<T> clase, final Object obj) {
        final String query = getCurrentMethod().getAnnotation(PruebaQuery.class).value();
        if (isQueryLoggeable(query)) {
            logSqlQueryObject(query, obj);
        }
 
        return namedJdbc.queryForObject(
                query,
                new BeanPropertySqlParameterSource(obj),
                clase);
    }
 
    protected final <T extends Object> List<T> queryForList(final Class<T> clase, final Object... params) {
        final String query = getCurrentMethod().getAnnotation(PruebaQuery.class).value();
        if (isQueryLoggeable(query)) {
            logSqlQueryParams(query, params);
        }
 
        if (ClassUtils.isPrimitiveOrWrapper(clase) || ClassUtils.isAssignable(String.class, clase)) {
            return jdbcTemplate.queryForList(query, clase, params);
        } else {
            return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(clase), params);
        }
    }
 
    protected final int update(final Object... params) {
        final String query = getCurrentMethod().getAnnotation(PruebaQuery.class).value();
        if (isQueryLoggeable(query)) {
            logSqlQueryParams(query, params);
        }
 
        return jdbcTemplate.update(query, params);
    }
 
    protected final <T extends Object> List<T> queryListNamed(final Map<String, ?> params, final Class<T> clase) {
        final String query = getCurrentMethod().getAnnotation(PruebaQuery.class).value();
        if (isQueryLoggeable(query)) {
            logSqlQueryMap(query, params);
        }
 
        if (ClassUtils.isPrimitiveOrWrapper(clase) || ClassUtils.isAssignable(String.class, clase)) {
            return namedJdbc.queryForList(query, params, clase);
        } else {
            return namedJdbc.query(query, params, BeanPropertyRowMapper.newInstance(clase));
        }
    }
 
    protected final int updateNamed(final Map<String, ?> params) {
        final String query = getCurrentMethod().getAnnotation(PruebaQuery.class).value();
        if (isQueryLoggeable(query)) {
            logSqlQueryMap(query, params);
        }
 
        return namedJdbc.update(query, params);
    }
 
    protected final int updateNamed(final Object obj) {
        final String query = getCurrentMethod().getAnnotation(PruebaQuery.class).value();
        if (isQueryLoggeable(query)) {
            logSqlQueryObject(query, obj);
        }
 
        return namedJdbc.update(query, new BeanPropertySqlParameterSource(obj));
    }
 
    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    protected final void updateRow(final Object... params) {
        final String query = getCurrentMethod().getAnnotation(PruebaQuery.class).value();
        if (isQueryLoggeable(query)) {
            logSqlQueryParams(query, params);
        }
 
        if (jdbcTemplate.update(query, params) != 1) {
            throw new DataIntegrityViolationException("Se intenta actualizar mas de una fila o ninguna");
        }
    }
 
    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    protected final void updateNamedRow(final Object obj) {
        final String query = getCurrentMethod().getAnnotation(PruebaQuery.class).value();
        if (isQueryLoggeable(query)) {
            logSqlQueryObject(query, obj);
        }
 
        if (namedJdbc.update(query, new BeanPropertySqlParameterSource(obj)) != 1) {
            throw new DataIntegrityViolationException("Se intenta actualizar mas de una fila o ninguna");
        }
    }
 
    @SuppressWarnings("PMD.UseLocaleWithCaseConversions")
    private boolean isQueryLoggeable(final String query) {
        return logSqlKeywords || keywords.stream().map(String::toLowerCase).noneMatch(query.toLowerCase()::contains);
    }
 
    protected static Object[] getFieldValues(final Object obj) {
        try {
            return Arrays.stream(obj.getClass().getDeclaredFields()).map(field -> getFieldValue(field, obj)).toArray();
        } catch (SecurityException exc) {
            throw new IllegalArgumentException(exc);
        }
    }
 
    private static Object getFieldValue(final Field field, final Object obj) {
        try {
            field.setAccessible(true); //NOPMD //NOSONAR
 
            return field.get(obj);
        } catch (IllegalAccessException | IllegalArgumentException exc) {
            throw new IllegalArgumentException(exc);
        }
    }
}
