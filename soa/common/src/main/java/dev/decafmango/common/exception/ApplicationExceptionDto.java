package dev.decafmango.common.exception;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "ApplicationException")
@XmlRootElement(name = "ApplicationException")
public class ApplicationExceptionDto {

    private String message;

}
