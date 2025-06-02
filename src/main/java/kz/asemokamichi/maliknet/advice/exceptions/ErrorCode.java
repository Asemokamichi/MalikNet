package kz.asemokamichi.maliknet.advice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorCode {

    public static final int NO_ERROR = 0;

    public static final int EXCEPTION  = 13;

    public static final int EMPTY_DATA = 1;

    public static final int INVALID_DATA = 3;

    public static final int CONFLICT = 4;
}
