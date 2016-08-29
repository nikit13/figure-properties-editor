package ru.spb.nkurasov.figure.editor;

import java.util.Optional;

import ru.spb.nkurasov.figure.FigurePropertyType;
import ru.spb.nkurasov.figure.StringPropertyType;

public class StringProperty implements FigureProperty {

    private final StringPropertyType type;

    private Optional<String> value;

    public StringProperty(StringPropertyType type) {
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

    public Optional<String> getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = Optional.ofNullable(value);
    }
}
