package dev.decafmango.service2.service;

import dev.decafmango.common.exception.ApplicationExceptionBuilder;
import dev.decafmango.common.mapper.cdi.OrganizationMapper;
import dev.decafmango.common.model.Address;
import dev.decafmango.common.model.Coordinates;
import dev.decafmango.common.model.Location;
import dev.decafmango.common.model.Organization;
import dev.decafmango.common.model.dto.organization.OrganizationDto;
import dev.decafmango.service2.client.Service1Client;
import dev.decafmango.service2.mapper.OrganizationDtoMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDate;

@Singleton
public class OrganizationService {

    @Inject
    private OrganizationMapper organizationMapper;

    @Inject
    private Service1Client service1Client;

    @Inject
    private OrganizationDtoMapper organizationDtoMapper;

    public OrganizationDto merge(int id1, int id2) throws Exception {
        if (id1 <= 0 || id2 <= 0) {
            throw ApplicationExceptionBuilder.badRequest("Ids must be greater than zero");
        }
        if (id1 == id2) {
            throw ApplicationExceptionBuilder.badRequest("You can not merge organization with itself");
        }

        Organization organization1 = getOrganizationById(id1);
        Organization organization2 = getOrganizationById(id2);

        Organization organization = new Organization(
                1,
                organization1.getName() + " " + organization2.getName(),
                new Coordinates(
                        0,
                        organization1.getCoordinates().getX(),
                        organization1.getCoordinates().getY()
                ),
                LocalDate.now(),
                organization1.getAnnualTurnover(),
                organization1.getFullName() + " " + organization2.getFullName(),
                (organization1.getEmployeesCount() == null ? 0 : organization1.getEmployeesCount())
                        + (organization2.getEmployeesCount() == null ? 0 : organization2.getEmployeesCount()),
                organization1.getType(),
                new Address(
                        0,
                        organization1.getPostalAddress().getStreet(),
                        new Location(
                                0,
                                organization1.getPostalAddress().getLocation().getX(),
                                organization1.getPostalAddress().getLocation().getY(),
                                organization1.getPostalAddress().getLocation().getName()
                        )
                )
        );

        organization = createOrganization(organization);

        return organizationMapper.toDto(organization);
    }

    public OrganizationDto hire(int id) throws Exception {
        Organization organization = getOrganizationById(id);
        if (organization.getEmployeesCount() == null) {
            organization.setEmployeesCount(1L);;
        } else {
            organization.setEmployeesCount(organization.getEmployeesCount() + 1);
        }

        organization = updateOrganizationById(id, organization);
        return organizationMapper.toDto(organization);
    }

    private Organization getOrganizationById(int id) throws Exception {
        Service1Client.Service1Response response = service1Client.getOrganizationById(id);

        if (response.status() != 200) {
            throw ApplicationExceptionBuilder.notFound("Organization with id %d not found".formatted(id));
        }

        return organizationMapper.toModel(organizationDtoMapper.parseXmlToOrganizationDto(response.body()));
    }

    private Organization createOrganization(Organization organization) throws Exception {
        Service1Client.Service1Response response = service1Client.createOrganization(organizationMapper.toDto(organization));

        if (response.status() != 201) {
            throw ApplicationExceptionBuilder.internalServerError(response.body());
        }

        return organizationMapper.toModel(organizationDtoMapper.parseXmlToOrganizationDto(response.body()));
    }

    private Organization updateOrganizationById(int id, Organization organization) throws Exception {
        Service1Client.Service1Response response = service1Client.updateOrganization(id, organizationMapper.toDto(organization));

        if (response.status() != 200) {
            throw ApplicationExceptionBuilder.internalServerError(response.body());
        }

        return organizationMapper.toModel(organizationDtoMapper.parseXmlToOrganizationDto(response.body()));
    }
}