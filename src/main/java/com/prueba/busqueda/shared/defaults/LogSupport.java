package com.prueba.busqueda.shared.defaults;

import com.prueba.busqueda.BusquedaApplication;
import com.prueba.busqueda.shared.infra.config.LoggerConfig;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author fhidalgo
 */
public interface LogSupport extends SqlLogSupport {
 
    String SQL = "SQL: ";
 
    default Logger getLogger() {
        return LoggerFactory.getLogger(BusquedaApplication.class);
    }
 
    default boolean isLogDebugEnabled() {
        return getLogger().isDebugEnabled();
    }
 
    default void logInfo(final String msg, final Object... objs) {
        getLogger().info(msg, objs);
    }
 
    default void logWarning(final String msg, final Object... objs) {
        getLogger().warn(msg, objs);
    }
 
    default void logError(final Throwable exc) {
        logError(exc, "Excehtp.on: {}");
    }
 
    default void logError(final Throwable exc, final String msg, final Object... objs) {
        getLogger().error(msg, exc);
    }
 
    default void logDebug(final String msg, final Object... objs) {
        getLogger().debug(msg, objs);
    }
 
    default void logSqlQueryObject(final String query, final Object obj) {
        logInfo(SQL + replaceObjetParams(query, obj));
    }
 
    default void logSqlQueryMap(final String query, final Map<String, ?> params) {
        logInfo(SQL + replaceMapParams(query, params));
    }
 
    default void logSqlQueryParams(final String query, final Object... params) {
        logInfo(SQL + replaceSqlParams(query, params));
    }
 
    default String getQueryParams(final LoggerConfig loggerConfig, final String queryParams) {
        try {
            if (queryParams == null) {
                return "";
            } else if (queryParams.startsWith("?")) {
                final StringBuilder queryParamsEnmascarados = new StringBuilder("?");
 
                final String[] params = queryParams.substring(1).split("&");
                int index = 0;
                for (final String param : params) {
                    final String key = param.split("=")[0];
                    final String value = param.split("=")[1];
 
                    if (loggerConfig.getListaNegraKeywords().contains(param.split("=")[0])) {
                        queryParamsEnmascarados.append(key)
                                .append('=')
                                .append(StringUtils.repeat("#", value.length()));
                    } else {
                        queryParamsEnmascarados.append(param);
 
                    }
                    index++;
                    if (index < params.length) {
                        queryParamsEnmascarados.append('&');
                    }
                }
 
                return queryParamsEnmascarados.toString();
            } else {
                return queryParams;
            }
        } catch (RuntimeException ex) {
            logError(ex);
            return queryParams;
        }
    }
 
}