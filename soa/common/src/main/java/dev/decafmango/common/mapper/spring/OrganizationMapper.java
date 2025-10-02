package dev.decafmango.common.mapper.spring;

import dev.decafmango.common.exception.ApplicationExceptionBuilder;
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

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {AddressMapper.class, CoordinatesMapper.class, LocationMapper.class})
public interface OrganizationMapper {

    OrganizationDto toDto(Organization organization);

    Organization toModel(FullOrganizationRequest fullOrganizationRequest);

    default Organization toModel(Organization oldOrg, PartialOrganizationRequest partialOrganizationRequest) {
        Organization newOrg = new Organization();
        newOrg.setId(oldOrg.getId());
        if (partialOrganizationRequest.getName() != null) {
            if (partialOrganizationRequest.getName().isBlank()) {
                throw ApplicationExceptionBuilder.badRequest("Имя: Поле не может быть null, Строка не может быть пустой");
            }
            newOrg.setName(partialOrganizationRequest.getName());
        } else {
            newOrg.setName(oldOrg.getName());
        }
        Coordinates newCoordinates = new Coordinates();
        if (partialOrganizationRequest.getCoordinates() != null) {
            Coordinates oldCoordinates = oldOrg.getCoordinates();
            CoordinatesDto coordinatesDto = partialOrganizationRequest.getCoordinates();
            if (coordinatesDto.getX() != null) {
                if (coordinatesDto.getX() > 258) {
                    throw ApplicationExceptionBuilder.badRequest("Координаты X: Максимальное значение поля: 258");
                }
                newCoordinates.setX(coordinatesDto.getX());
            } else {
                newCoordinates.setX(oldCoordinates.getX());
            }
            if (coordinatesDto.getY() != null) {
                if (coordinatesDto.getY() < -148) {
                    throw ApplicationExceptionBuilder.badRequest("Координаты Y: Значение поля должно быть больше -149");
                }
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
            if (partialOrganizationRequest.getAnnualTurnover() <= 0) {
                throw ApplicationExceptionBuilder.badRequest("Годовой оборот: Значение поля должно быть больше 0");
            }
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
            if (partialOrganizationRequest.getEmployeesCount() <= 0) {
                throw ApplicationExceptionBuilder.badRequest("Количество сотрудников : Значение поля должно быть больше 0");
            }
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
        Address oldAddress = oldOrg.getPostalAddress();
        if (partialOrganizationRequest.getPostalAddress() != null) {
            AddressDto addressDto = partialOrganizationRequest.getPostalAddress();
            if (addressDto.getStreet() != null) {
                if (addressDto.getStreet().isBlank()) {
                    throw ApplicationExceptionBuilder.badRequest("Улица: Строка не может быть пустой, Поле может быть null");
                }
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
        } else {
            newAddress.setStreet(oldAddress.getStreet());
            Location newLocation = new Location();
            Location oldLocation = oldAddress.getLocation();
            newLocation.setX(oldLocation.getX());
            newLocation.setY(oldLocation.getY());
            newLocation.setName(oldLocation.getName());
            newAddress.setLocation(newLocation);
        }
        newOrg.setPostalAddress(newAddress);

        return newOrg;
    }

}
