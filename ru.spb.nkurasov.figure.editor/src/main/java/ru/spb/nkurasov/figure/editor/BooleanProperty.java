package ru.spb.nkurasov.figure.editor;

import ru.spb.nkurasov.figure.BooleanPropertyType;
import ru.spb.nkurasov.figure.FigurePropertyType;

public class BooleanProperty implements FigureProperty {

    private final BooleanPropertyType type;

    private Boolean value;

    public BooleanProperty(BooleanPropertyType type) {
        if (type == null) {
            throw new IllegalArgumentException("property type is null");
        }
        this.type = type;

        if (type.hasDefaultValue()) {
            this.value = type.getDefaultValue();
        }
    }

    @Override
    public FigurePropertyType getType() {
        return type;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
