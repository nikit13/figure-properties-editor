package ru.spb.nkurasov.figure.editor;

import ru.spb.nkurasov.figure.FigurePropertyType;
import ru.spb.nkurasov.figure.StringPropertyType;

public class StringProperty implements FigureProperty {

    private final StringPropertyType type;
    
    public StringProperty(StringPropertyType type) {
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
