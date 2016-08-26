package ru.spb.nkurasov.figure;

public class StringPropertyType extends AbstractFigureProperty {

    private final String defaultValue;

    StringPropertyType(String name, String defaultValue) {
        super(name);

        this.defaultValue = defaultValue;
    }

    @Override
    public void accept(FigurePropertyVisitor visitor) {
        visitor.visit(this);
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
