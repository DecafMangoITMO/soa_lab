package dev.decafmango.common.mapper.spring;

import dev.decafmango.common.model.Location;
import dev.decafmango.common.model.dto.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationMapper {

    LocationDto toDto(Location location);

    Location toModel(LocationDto dto);

}
