package ru.spb.nkurasov.figure;

public class StringPropertyType extends AbstractFigurePropertyType {

    private final String defaultValue;

    StringPropertyType(String name, String defaultValue) {
        super(name);

        this.defaultValue = defaultValue;
    }

    @Override
    public void accept(FigurePropertyTypeVisitor visitor) {
        visitor.visit(this);
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
