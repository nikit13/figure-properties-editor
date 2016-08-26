package ru.spb.nkurasov.figure;

public class ReadFigureException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    ReadFigureException() {
        super();
    }

    ReadFigureException(String message, Throwable cause) {
        super(message, cause);
    }

    ReadFigureException(String message) {
        super(message);
    }

    ReadFigureException(Throwable cause) {
        super(cause);
    }
}
