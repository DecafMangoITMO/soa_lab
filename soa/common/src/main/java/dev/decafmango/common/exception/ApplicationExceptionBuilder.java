package dev.decafmango.common.exception;

public class ApplicationExceptionBuilder {

    public static ApplicationException badRequest(String message) {
        return new ApplicationException(ApplicationException.ExceptionType.BAD_REQUEST, message);
    }

    public static ApplicationException notFound(String message) {
        return new ApplicationException(ApplicationException.ExceptionType.OBJECT_NOT_FOUND, message);
    }

    public static ApplicationException internalServerError(String message) {
        return new ApplicationException(ApplicationException.ExceptionType.INTERNAL_SERVER_ERROR, message);
    }

}
