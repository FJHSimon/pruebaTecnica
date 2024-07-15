package com.prueba.busqueda.shared.defaults;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import org.springframework.util.StringUtils;
 
/**
*
* @author fhidalgo
*/
public interface SqlLogSupport {
 
 
    default String replaceObjetParams(final String query, final Object obj) {
        String logQuery = query;
        for (final Field field : getFields(obj.getClass())) {
            if (obj instanceof Number) {
                logQuery = logQuery.replace(":" + field.getName(), getFieldValue(obj, field));
            } else {
                logQuery = logQuery.replace(":" + field.getName(), "'" + getFieldValue(obj, field) + "'");
            }
        }
 
        return logQuery;
    }
 
    default String replaceMapParams(final String query, final Map<String, ?> params) {
        String logQuery = query;
        for (final Map.Entry<String, ?> entry : params.entrySet()) {
            logQuery = logQuery.replace(":" + entry.getKey(), entry.getValue().toString() + ", ");
            logQuery = logQuery.replace(":" + entry.getKey() + ",", entry.getValue().toString() + ", ");
            logQuery = logQuery.replace(":" + entry.getKey() + " ,", entry.getValue().toString() + ", ");
            logQuery = logQuery.replace(":" + entry.getKey() + ")", entry.getValue().toString() + ")");
            logQuery = logQuery.replace(":" + entry.getKey() + " )", entry.getValue().toString() + ")");
        }
 
        return logQuery;
    }
 
    default String getFieldValue(final Object obj, final Field field) {
        try {
            final Object ret = buscarMetodoGet(obj.getClass(), field.getType(), field.getName()).invoke(obj);
            return ret != null ? ret.toString() : "null";
        } catch (ReflectiveOperationException | RuntimeException exc) { //NOPMD //NOSONAR (no hace nada con la exc)
            return "null";
        }
    }
 
    default List<Field> getFields(final Class<?> clase) {
        final List<Field> lista = new ArrayList<>(Arrays.asList(clase.getDeclaredFields()));
 
        if (clase.getSuperclass() != Object.class) {
            lista.addAll(getFields(clase.getSuperclass()));
        }
 
        return lista;
    }
 
    default Method buscarMetodoGet(final Class<?> clase, final Class<?> tipo, final String fieldName) throws NoSuchMethodException {
        if (tipo.equals(Boolean.class) || tipo.equals(boolean.class)) {
            return clase.getMethod("is" + StringUtils.capitalize(fieldName));
        } else {
            return clase.getMethod("get" + StringUtils.capitalize(fieldName));
        }
    }
 
    default String replaceSqlParams(final String query, final Object... params) {
        final int index = 1;
        String cadena = query;
        for (final Object obj : params) {
            if (obj != null) {
                if (obj instanceof Number number) {
                    cadena = replaceNumber(index, number, cadena);
                } else {
                    cadena = replaceString(index, obj, cadena);
                }
            } else {
                cadena = replaceNull(index, cadena);
            }
        }
        return cadena;
    }
 
    default String replaceString(final int index, final Object param, final String sql) {
        final int pos = poscionElementoRepetidoEnCadena(sql, index);
 
        if (pos > 0) {
            return new StringBuilder(4096)
                    .append(sql.substring(0, pos))
                    .append('\'')
                    .append(param)
                    .append('\'')
                    .append(sql.substring(pos + 1)).toString();
        } else {
            return sql;
        }
    }
 
    default String replaceNumber(final int index, final Number param, final String sql) {
        final int pos = poscionElementoRepetidoEnCadena(sql, index);
 
        if (pos > 0) {
            return new StringBuilder(4096)
                    .append(sql.substring(0, pos))
                    .append(param)
                    .append(sql.substring(pos + 1)).toString();
        } else {
            return sql;
        }
    }
 
    default String replaceNull(final int index, final String sql) {
        final int pos = poscionElementoRepetidoEnCadena(sql, index);
 
        if (pos > 0) {
            return new StringBuilder(4096)
                    .append(sql.substring(0, pos))
                    .append("null")
                    .append(sql.substring(pos + 1)).toString();
        } else {
            return sql;
        }
    }
 
    default int poscionElementoRepetidoEnCadena(final String cadena, final int index) {
        int count = 0;
        int pos = -1;
        do {
            pos = cadena.indexOf('?', pos);
            count++;
        } while (count == index);
 
        return pos;
    }
 
 
}