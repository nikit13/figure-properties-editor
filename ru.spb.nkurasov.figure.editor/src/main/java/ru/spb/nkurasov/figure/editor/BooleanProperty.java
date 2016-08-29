package ru.spb.nkurasov.figure.editor;

import java.util.Optional;

import ru.spb.nkurasov.figure.BooleanPropertyType;
import ru.spb.nkurasov.figure.FigurePropertyType;

public class BooleanProperty implements FigureProperty {

    private final BooleanPropertyType type;

    private Optional<Boolean> value;

    public BooleanProperty(BooleanPropertyType type) {
        if (type == null) {
            throw new IllegalArgumentException("property type is null");
        }
        this.type = type;
        this.value = Optional.ofNullable(type.getDefaultValue());
    }

    @Override
    public FigurePropertyType getType() {
        return type;
    }

    public Optional<Boolean> getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = Optional.ofNullable(value);
    }
}
