package ru.spb.nkurasov.figure.editor;

import ru.spb.nkurasov.figure.FigurePropertyType;

public interface FigureProperty {

    FigurePropertyType getType();
    
    default String getName() {
        return getType().getName();
    }
}
