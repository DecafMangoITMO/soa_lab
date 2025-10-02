package dev.decafmango.common.exception;

import lombok.Data;

@Data
public class ApplicationException extends RuntimeException {

    private final ExceptionType type;

    public ApplicationException(ExceptionType type, String message) {
        super(message);

        this.type = type;
    }

    public enum ExceptionType {
        BAD_REQUEST,
        OBJECT_NOT_FOUND,
        INTERNAL_SERVER_ERROR,
    }

}
