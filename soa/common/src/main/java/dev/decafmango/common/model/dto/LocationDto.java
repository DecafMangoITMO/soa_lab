package dev.decafmango.common.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
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
public class LocationDto {

    @NotNull(message = "Локация X: Поле не может быть null")
    private Double x;

    @NotNull(message = "Локация Y: Поле не может быть null")
    private Float y;

    @NotNull(message = "Локация имя: Поле не может быть null")
    private String name;

}
