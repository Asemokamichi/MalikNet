package kz.asemokamichi.maliknet.advice;

import kz.asemokamichi.maliknet.advice.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler(InvalidRequest.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest(InvalidRequest e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.EXCEPTION, e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFound e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.EXCEPTION, e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(AlreadyExists e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.EXCEPTION, e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
