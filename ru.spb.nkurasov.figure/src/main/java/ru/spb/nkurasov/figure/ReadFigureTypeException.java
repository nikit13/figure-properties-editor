package ru.spb.nkurasov.figure;

/**
 * Ошибка при чтении типов фигур
 * 
 * @author nkurasov
 *
 */
public class ReadFigureTypeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    ReadFigureTypeException() {
        super();
    }

    ReadFigureTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    ReadFigureTypeException(String message) {
        super(message);
    }

    ReadFigureTypeException(Throwable cause) {
        super(cause);
    }
}
