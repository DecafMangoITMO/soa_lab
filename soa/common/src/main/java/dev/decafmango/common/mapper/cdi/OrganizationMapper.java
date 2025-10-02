package dev.decafmango.common.mapper.cdi;

import dev.decafmango.common.model.Address;
import dev.decafmango.common.model.Coordinates;
import dev.decafmango.common.model.Location;
import dev.decafmango.common.model.Organization;
import dev.decafmango.common.model.dto.AddressDto;
import dev.decafmango.common.model.dto.CoordinatesDto;
import dev.decafmango.common.model.dto.LocationDto;
import dev.decafmango.common.model.dto.organization.FullOrganizationRequest;
import dev.decafmango.common.model.dto.organization.OrganizationDto;
import dev.decafmango.common.model.dto.organization.PartialOrganizationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {AddressMapper.class, CoordinatesMapper.class, LocationMapper.class})
public interface OrganizationMapper {

    Organization toModel(OrganizationDto organizationDto);

    OrganizationDto toDto(Organization organization);

    Organization toModel(FullOrganizationRequest fullOrganizationRequest);

    default Organization toModel(Organization oldOrg, PartialOrganizationRequest partialOrganizationRequest) {
        Organization newOrg = new Organization();
        newOrg.setId(oldOrg.getId());
        if (partialOrganizationRequest.getName() != null) {
            newOrg.setName(partialOrganizationRequest.getName());
        } else {
            newOrg.setName(oldOrg.getName());
        }
        Coordinates newCoordinates = new Coordinates();
        if (partialOrganizationRequest.getCoordinates() != null) {
            Coordinates oldCoordinates = oldOrg.getCoordinates();
            CoordinatesDto coordinatesDto = partialOrganizationRequest.getCoordinates();
            if (coordinatesDto.getX() != null) {
                newCoordinates.setX(coordinatesDto.getX());
            } else {
                newCoordinates.setX(oldCoordinates.getX());
            }
            if (coordinatesDto.getY() != null) {
                newCoordinates.setY(coordinatesDto.getY());
            } else {
                newCoordinates.setY(oldCoordinates.getY());
            }
        } else {
            newCoordinates.setX(oldOrg.getCoordinates().getX());
            newCoordinates.setY(oldOrg.getCoordinates().getY());
        }
        newOrg.setCoordinates(newCoordinates);
        newOrg.setCreationDate(oldOrg.getCreationDate());
        if (partialOrganizationRequest.getAnnualTurnover() != null) {
            newOrg.setAnnualTurnover(partialOrganizationRequest.getAnnualTurnover());
        } else {
            newOrg.setAnnualTurnover(oldOrg.getAnnualTurnover());
        }
        if (partialOrganizationRequest.getFullName() != null) {
            newOrg.setFullName(partialOrganizationRequest.getFullName());
        } else {
            newOrg.setFullName(oldOrg.getFullName());
        }
        if (partialOrganizationRequest.getEmployeesCount() != null) {
            newOrg.setEmployeesCount(partialOrganizationRequest.getEmployeesCount());
        } else {
            newOrg.setEmployeesCount(oldOrg.getEmployeesCount());
        }
        if (partialOrganizationRequest.getType() != null) {
            newOrg.setType(partialOrganizationRequest.getType());
        } else {
            newOrg.setType(oldOrg.getType());
        }
        Address newAddress = new Address();
        if (partialOrganizationRequest.getPostalAddress() != null) {
            Address oldAddress = oldOrg.getPostalAddress();
            AddressDto addressDto = partialOrganizationRequest.getPostalAddress();
            if (addressDto.getStreet() != null) {
                newAddress.setStreet(addressDto.getStreet());
            } else {
                newAddress.setStreet(oldAddress.getStreet());
            }
            Location newLocation = new Location();
            if (addressDto.getLocation() != null) {
                Location oldLocation = oldAddress.getLocation();
                LocationDto locationDto = addressDto.getLocation();
                if (locationDto.getName() != null) {
                    newLocation.setName(locationDto.getName());
                } else {
                    newLocation.setName(oldLocation.getName());
                }
                if (locationDto.getX() != null) {
                    newLocation.setX(locationDto.getX());
                } else {
                    newLocation.setX(oldLocation.getX());
                }
                if (locationDto.getY() != null) {
                   newLocation.setY(locationDto.getY());
                } else {
                    newLocation.setY(oldLocation.getY());
                }
            }
            newAddress.setLocation(newLocation);
        }
        newOrg.setPostalAddress(newAddress);

        return newOrg;
    }

}
