package dev.decafmango.common.mapper.spring;

import dev.decafmango.common.model.Coordinates;
import dev.decafmango.common.model.dto.CoordinatesDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CoordinatesMapper {

    CoordinatesDto toDto(Coordinates coordinates);

    Coordinates toModel(CoordinatesDto dto);

}
