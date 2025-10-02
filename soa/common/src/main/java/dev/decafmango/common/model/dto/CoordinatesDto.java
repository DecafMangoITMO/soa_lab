package dev.decafmango.common.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlRootElement
public class CoordinatesDto {

    @NotNull(message = "Координаты X: Поле не может быть null")
    @Max(value = 258, message = "Координаты X: Максимальное значение поля: 258")
    private Float x;

    @NotNull(message = "Координаты Y: Поле не может быть null")
    @Min(value = -148, message = "Координаты Y: Значение поля должно быть больше -149")
    private Long y;

}
