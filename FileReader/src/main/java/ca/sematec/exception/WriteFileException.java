package ca.sematec.exception;

import java.io.IOException;

public class WriteFileException extends RuntimeException {
    public WriteFileException(String message) {
        super(message);
    }

    public WriteFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
