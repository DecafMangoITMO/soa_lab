package dev.decafmango.service1.util;

import dev.decafmango.common.exception.ApplicationExceptionBuilder;
import dev.decafmango.common.model.OrganizationType;

import java.time.LocalDate;

public enum OrganizationField {

    ID("id", "id", Integer.class),
    NAME("name", "name", String.class),
    COORDINATES_X("coordinates.x", "coordinates.x", Float.class),
    COORDINATES_Y("coordinates.y", "coordinates.y", Long.class),
    CREATION_DATE("creationDate", "creation-date", LocalDate.class),
    ANNUAL_TURNOVER("annualTurnover", "annual-turnover", Double.class),
    FULL_NAME("fullName", "full-name", String.class),
    EMPLOYEES_COUNT("employeesCount", "employees-count", Long.class),
    TYPE("type", "type", OrganizationType.class),
    POSTAL_ADDRESS_STREET("PostalAddress.street", "postal-address.street", String.class),
    POSTAL_ADDRESS_LOCATION_X("PostalAddress.location.x", "postal-address.location.x", Double.class),
    POSTAL_ADDRESS_LOCATION_Y("PostalAddress.location.y", "postal-address.location.y", Float.class),
    POSTAL_ADDRESS_LOCATION_NAME("PostalAddress.location.name", "postal-address.location.name", String.class);

    private String name;
    private String path;
    private Class<?> type;

    OrganizationField(String name, String path, Class<?> type) {
        this.name = name;
        this.path = path;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Object parse(String value) {
        try {
            if (type == Integer.class) {
                return Integer.parseInt(value);
            }
            if (type == Long.class) {
                return Long.parseLong(value);
            }
            if (type == Float.class) {
                return Float.parseFloat(value);
            }
            if (type == Double.class) {
                return Double.parseDouble(value);
            }
            if (type == LocalDate.class) {
                return LocalDate.parse(value);
            }
            if (type == OrganizationType.class) {
                return OrganizationType.valueOf(value);
            }
            if (type == String.class) {
                return value;
            }
            throw new IllegalArgumentException("Unsupported type: %s".formatted(type));
        } catch (Exception e) {
            throw ApplicationExceptionBuilder.badRequest("Illegal value for field %s: %s".formatted(path, value));
        }
    }

}
