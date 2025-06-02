package kz.asemokamichi.maliknet.advice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Slf4j
public class InvalidRequest  extends RuntimeException{
    public InvalidRequest(String message) {
        super(message);
        log.error(message);
    }
}
