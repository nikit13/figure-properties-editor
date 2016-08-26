package ru.spb.nkurasov.figure;

public class IntegerPropertyType extends AbstractFigureProperty {

    private final Integer defaultValue;

    IntegerPropertyType(String name, Integer defaultValue) {
        super(name);

        this.defaultValue = defaultValue;
    }

    @Override
    public void accept(FigurePropertyVisitor visitor) {
        visitor.visit(this);
    }

    public Integer getDefaultValue() {
        return defaultValue;
    }
}
