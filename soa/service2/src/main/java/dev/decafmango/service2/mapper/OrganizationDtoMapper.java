package dev.decafmango.service2.mapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.decafmango.common.model.dto.organization.OrganizationDto;
import jakarta.inject.Singleton;

import java.io.StringWriter;

@Singleton
public class OrganizationDtoMapper {

    public String generateXmlBody(OrganizationDto organizationDto) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();

        // Регистрием модуль для Java 8 Date/Time API
        xmlMapper.registerModule(new JavaTimeModule());

        // Отключаем запись дат как timestamp
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Дополнительная конфигурация
        xmlMapper.findAndRegisterModules();

        // Конфигурация (опционально)
        xmlMapper.findAndRegisterModules();

        // Преобразование объекта в XML строку
        StringWriter writer = new StringWriter();
        xmlMapper.writeValue(writer, organizationDto);

        return writer.toString();
    }

    public OrganizationDto parseXmlToOrganizationDto(String xml) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();

        // Регистрируем модуль для Java 8 Date/Time API
        xmlMapper.registerModule(new JavaTimeModule());

        // Дополнительная конфигурация (если нужно)
        xmlMapper.findAndRegisterModules();

        // Преобразование XML строки в объект
        return xmlMapper.readValue(xml, OrganizationDto.class);
    }

}
