package dev.decafmango.common.mapper.cdi;

import dev.decafmango.common.model.Location;
import dev.decafmango.common.model.dto.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationMapper {

    LocationDto toDto(Location location);

    Location toModel(LocationDto dto);

}
