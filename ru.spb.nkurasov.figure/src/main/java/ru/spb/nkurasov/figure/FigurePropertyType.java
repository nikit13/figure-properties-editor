package ru.spb.nkurasov.figure;

/**
 * Тип свойства фигуры. В зависимости от конкретного типа может иметь
 * дополнительные атрибуты
 * 
 * @author nkurasov
 * 
 * @sealed этот интерфейс не предназначен для реализации его классами вне
 *         текущего пакета
 */
public interface FigurePropertyType {

    // TODO maybe add optionality?

    String getName();

    void accept(FigurePropertyTypeVisitor visitor);

}
