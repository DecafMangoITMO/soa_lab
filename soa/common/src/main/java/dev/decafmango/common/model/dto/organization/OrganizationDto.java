package dev.decafmango.common.model.dto.organization;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import dev.decafmango.common.model.OrganizationType;
import dev.decafmango.common.model.dto.AddressDto;
import dev.decafmango.common.model.dto.CoordinatesDto;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JacksonXmlRootElement(localName = "Organization")
@XmlRootElement
public class OrganizationDto {

    private int id;

    private String name;

    private CoordinatesDto coordinates;

    private LocalDate creationDate;

    private Double annualTurnover;

    private String fullName;

    private Long employeesCount;

    private OrganizationType type;

    private AddressDto postalAddress;

}
