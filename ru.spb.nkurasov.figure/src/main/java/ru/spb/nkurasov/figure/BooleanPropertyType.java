package ru.spb.nkurasov.figure;

public class BooleanPropertyType extends AbstractFigureProperty {

    private final Boolean defaultValue;

    BooleanPropertyType(String name, Boolean defaultValue) {
        super(name);

        this.defaultValue = defaultValue;
    }

    @Override
    public void accept(FigurePropertyVisitor visitor) {
        visitor.visit(this);
    }

    public Boolean getDefaultValue() {
        return defaultValue;
    }
}
