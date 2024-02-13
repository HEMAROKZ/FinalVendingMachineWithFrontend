package com.VendingMachine.customeexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class VendingMachineControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<VendingMachineException> codeNotFoundExceptionController(ProductIdNotFoundException productIdNotFoundException){

        VendingMachineException vendingMachineException = new VendingMachineException(
                productIdNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND); //suitable http status can be kept
        return new ResponseEntity<>(vendingMachineException,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<VendingMachineException> NoExactChangeExceptionHandler(NoExactChangeException noExactChangeException){

        VendingMachineException vendingMachineException = new VendingMachineException(
                noExactChangeException.getMessage(),
                HttpStatus.NOT_FOUND); //suitable http status can be kept
        return new ResponseEntity<>(vendingMachineException,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<VendingMachineException> productUnavialiableExceptionController(ProductUnavialableException productUnavialableException){

        VendingMachineException vendingMachineException = new VendingMachineException(
                productUnavialableException.getMessage(),
                HttpStatus.NOT_FOUND); //suitable http status can be kept
        return new ResponseEntity<>(vendingMachineException,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {InsufficientInitialBalanceException.class})
    public ResponseEntity<VendingMachineException> InsufficientAmountExceptionHandler(InsufficientInitialBalanceException insufficientInitialBalanceException) {

        VendingMachineException vendingMachineException = new VendingMachineException(
                insufficientInitialBalanceException.getMessage(),
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(vendingMachineException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ProductAlreadyExist.class})
    public ResponseEntity<VendingMachineException> ProductAlreadyExist(ProductAlreadyExist productAlreadyExist) {

        VendingMachineException vendingMachineException = new VendingMachineException(
                productAlreadyExist.getMessage(),
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(vendingMachineException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InsufficientInputCashException.class})
    public ResponseEntity<VendingMachineException> InsufficientChangeExceptionHandler(InsufficientInputCashException insufficientInputCashException) {

        VendingMachineException vendingMachineException = new VendingMachineException(
                insufficientInputCashException.getMessage(),
                //this is server side error so changed to error from bad request to  http code 500
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(vendingMachineException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {CustomIOException.class})
    public ResponseEntity<VendingMachineException> CustomIOExceptionHandler(CustomIOException customIOException) {

        VendingMachineException vendingMachineException = new VendingMachineException(
                customIOException.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(vendingMachineException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
