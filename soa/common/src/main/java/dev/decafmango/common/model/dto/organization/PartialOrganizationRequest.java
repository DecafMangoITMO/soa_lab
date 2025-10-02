package dev.decafmango.common.model.dto.organization;

import dev.decafmango.common.model.OrganizationType;
import dev.decafmango.common.model.dto.AddressDto;
import dev.decafmango.common.model.dto.CoordinatesDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PartialOrganizationRequest {

    private String name;

    private CoordinatesDto coordinates;

    private Double annualTurnover;

    private String fullName;

    private Long employeesCount;

    private OrganizationType type;

    private AddressDto postalAddress;

}
