package kz.asemokamichi.maliknet.advice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private int errorCode;
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
