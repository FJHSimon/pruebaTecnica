package com.prueba.busqueda.infra.interceptor;

import com.prueba.busqueda.shared.defaults.LogSupport;
import com.prueba.busqueda.shared.infra.config.LoggerConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 * @author fhidalgo
 */
@Component
public class WsRequestInterceptor implements HandlerInterceptor, LogSupport {

    @Autowired
    public LoggerConfig loggerConfig;
    
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object object) throws IOException {
        this.logRequest(request);

        return true;
    }

    private void logRequest(final HttpServletRequest request) {
        logInfo("*** REST Request -> Request URL:{}   Remote address:{} HTTP method:{} Content type:{} Headers:{}",
                request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + getQueryParams(loggerConfig, request.getQueryString()) : ""),
                request.getRemoteAddr(),
                request.getMethod(),
                request.getContentType(),
                Collections.list(request.getHeaderNames())
                        .stream()
                        .map(header -> getHeaderParam(header, request.getHeader(header)))
                        .collect(Collectors.joining("; ")));
    }
 
    private String getHeaderParam(final String header, final String value) {
        if (loggerConfig.getListaNegraKeywords().contains(header)) {
            return header + "=" + StringUtils.repeat("*", value.length());
        } else {
            return header + "=" + value;
        }
    }

}
