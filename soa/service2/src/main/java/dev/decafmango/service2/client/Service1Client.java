package dev.decafmango.service2.client;

import dev.decafmango.common.model.dto.organization.OrganizationDto;
import dev.decafmango.service2.mapper.OrganizationDtoMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
public class Service1Client {

    private Client client;
    private static final String baseUrl = "https://localhost:8181/service-1-0.0.1-SNAPSHOT/api/v1";

    @Inject
    private OrganizationDtoMapper organizationDtoMapper;

    public Service1Client() {
        this.client = ClientBuilder.newClient();
    }

    public Service1Response getOrganizationById(int id) {
        Response response = client.target(baseUrl + "/organizations/" + id).request().get();
        int status = response.getStatus();
        String responseBody = response.readEntity(String.class);

        return new Service1Response(
                status,
                responseBody
        );
    }

    public Service1Response createOrganization(OrganizationDto organizationDto) throws Exception {
        String xmlBody = organizationDtoMapper.generateXmlBody(organizationDto);

        Response response = client.target(baseUrl + "/organizations")
                .request(MediaType.APPLICATION_XML)
                .post(Entity.entity(xmlBody, MediaType.APPLICATION_XML));

        int status = response.getStatus();
        String responseBody = response.readEntity(String.class);

        return new Service1Response(status, responseBody);
    }

    public Service1Response updateOrganization(int id, OrganizationDto organizationDto) throws Exception {
        String xmlBody = organizationDtoMapper.generateXmlBody(organizationDto);

        Response response = client.target(baseUrl + "/organizations/" + id)
                .request(MediaType.APPLICATION_XML)
                .put(Entity.entity(xmlBody, MediaType.APPLICATION_XML));

        int status = response.getStatus();
        String responseBody = response.readEntity(String.class);

        return new Service1Response(status, responseBody);
    }

    public record Service1Response(int status, String body){}

}
