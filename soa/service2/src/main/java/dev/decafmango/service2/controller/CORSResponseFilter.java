package dev.decafmango.service2.controller;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CORSResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) {

        // Явно указываем разрешенные origins
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");

        // Явно перечисляем методы вместо *
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "*");

        responseContext.getHeaders().add("Access-Control-Allow-Headers", "*");

        // Кэшируем preflight на 1 час
        responseContext.getHeaders().add("Access-Control-Max-Age", "3600");

    }
}