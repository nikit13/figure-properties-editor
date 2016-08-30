package ru.spb.nkurasov.figure.editor;

import java.util.Optional;

import ru.spb.nkurasov.figure.FigurePropertyType;
import ru.spb.nkurasov.figure.IntegerPropertyType;

public class IntegerProperty implements FigureProperty {

    private final IntegerPropertyType type;

    private Optional<Integer> value;

    public IntegerProperty(IntegerPropertyType type) {
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

    @Override
    public void accept(FigurePropertyVisitor visitor) {
        visitor.visit(this);
    }

    public Optional<Integer> getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = Optional.ofNullable(value);
    }
}
