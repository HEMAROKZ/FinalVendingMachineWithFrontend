package com.VendingMachine.customeexception;
import org.springframework.http.HttpStatus;

public class VendingMachineException {

    private final String message;
    private final HttpStatus httpStatus;

    public VendingMachineException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
