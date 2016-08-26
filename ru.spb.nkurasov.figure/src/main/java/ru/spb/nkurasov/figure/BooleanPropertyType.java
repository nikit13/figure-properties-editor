package ru.spb.nkurasov.figure;

public class BooleanPropertyType extends AbstractPropertyType<Boolean> {

    BooleanPropertyType() {
        super();
    }

    BooleanPropertyType(Boolean defaultValue) {
        super(defaultValue);
    }

    @Override
    public void accept(PropertyTypeVisitor visitor) {
        visitor.visit(this);
    }
}
