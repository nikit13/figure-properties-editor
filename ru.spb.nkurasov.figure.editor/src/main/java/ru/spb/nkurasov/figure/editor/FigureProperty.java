package ru.spb.nkurasov.figure.editor;

import ru.spb.nkurasov.figure.FigurePropertyType;

/**
 * Свойство фигуры. Имеет описание (тип) свойства
 * 
 * @author nkurasov
 *
 * @sealed этот интерфейс не предназначен для реализации его классами вне
 *         текущего пакета
 */
public interface FigureProperty {

    FigurePropertyType getType();

    void accept(FigurePropertyVisitor visitor);

    default String getName() {
        return getType().getName();
    }
}
