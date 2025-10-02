package dev.decafmango.service2.exception;

import dev.decafmango.common.exception.ApplicationException;
import dev.decafmango.common.exception.ApplicationExceptionDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApplicationExceptionHandler implements ExceptionMapper<ApplicationException> {

    @Override
    public Response toResponse(ApplicationException e) {
        ApplicationExceptionDto applicationExceptionDto = new ApplicationExceptionDto(

        );
        applicationExceptionDto.setMessage(e.getMessage());
        return switch (e.getType()) {
            case BAD_REQUEST -> Response.status(400).entity(applicationExceptionDto).build();
            case OBJECT_NOT_FOUND -> Response.status(404).entity(applicationExceptionDto).build();
            case INTERNAL_SERVER_ERROR -> Response.status(500).entity(applicationExceptionDto).build();
        };
    }

}
