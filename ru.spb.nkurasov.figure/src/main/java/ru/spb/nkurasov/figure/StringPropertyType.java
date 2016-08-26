package ru.spb.nkurasov.figure;

public class StringPropertyType extends AbstractPropertyType<String> {

    StringPropertyType() {
        super();
    }

    StringPropertyType(String defaultValue) {
        super(defaultValue);
    }

    @Override
    public void accept(PropertyTypeVisitor visitor) {
        visitor.visit(this);
    }
}
