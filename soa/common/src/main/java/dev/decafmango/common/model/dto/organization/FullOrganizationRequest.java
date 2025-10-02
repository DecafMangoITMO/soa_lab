package dev.decafmango.common.model.dto.organization;

import dev.decafmango.common.model.OrganizationType;
import dev.decafmango.common.model.dto.AddressDto;
import dev.decafmango.common.model.dto.CoordinatesDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class FullOrganizationRequest {

    @NotBlank(message = "Имя: Поле не может быть null, Строка не может быть пустой")
    private String name;

    @NotNull(message = "Координаты: Поле не может быть null")
    @Valid
    private CoordinatesDto coordinates;

    @NotNull(message = "Годовой оборот: Поле не может быть null")
    @Min(value = 1, message = "Годовой оборот: начение поля должно быть больше 0")
    private Double annualTurnover;

    private String fullName;

    @Positive(message = "Количество сотрудников: Значение поля должно быть больше 0")
    private Long employeesCount;

    @NotNull(message = "Тип: Поле не может быть null")
    private OrganizationType type;

    @NotNull(message = "Адрес: Поле не может быть null")
    @Valid
    private AddressDto postalAddress;

}
