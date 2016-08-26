package ru.spb.nkurasov.figure;

public class IntegerPropertyType extends AbstractPropertyType<Integer> {

    IntegerPropertyType() {
        super();
    }

    IntegerPropertyType(Integer defaultValue) {
        super(defaultValue);
    }

    @Override
    public void accept(PropertyTypeVisitor visitor) {
        visitor.visit(this);
    }
}
