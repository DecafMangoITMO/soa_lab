package dev.decafmango.common.mapper.cdi;

import dev.decafmango.common.model.Address;
import dev.decafmango.common.model.dto.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    AddressDto toDto(Address address);

    Address toModel(AddressDto dto);

}
