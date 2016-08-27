package ru.spb.nkurasov.figure.editor;

import ru.spb.nkurasov.figure.FigurePropertyType;
import ru.spb.nkurasov.figure.IntegerPropertyType;

public class IntegerProperty implements FigureProperty {

    private final IntegerPropertyType type;
    
    public IntegerProperty(IntegerPropertyType type) {
        if (type == null) {
            throw new IllegalArgumentException("property type is null");
        }
        this.type = type;
    }
    
    @Override
    public FigurePropertyType getType() {
        return type;
    }
}
