package dev.decafmango.common.mapper.cdi;

import dev.decafmango.common.model.Coordinates;
import dev.decafmango.common.model.dto.CoordinatesDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CoordinatesMapper {

    CoordinatesDto toDto(Coordinates coordinates);

    Coordinates toModel(CoordinatesDto dto);

}
