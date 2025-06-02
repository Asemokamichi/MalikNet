package kz.asemokamichi.maliknet.advice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Slf4j
public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String message) {
        super(message);
        log.error(message);
    }
}