package ru.spb.nkurasov.figure;

public class BooleanPropertyDefinition extends AbstractFigureProperty {

    private final Boolean defaultValue;

    BooleanPropertyDefinition(String name, Boolean defaultValue) {
        super(name);

        this.defaultValue = defaultValue;
    }

    @Override
    public void accept(FigurePropertyDefinitionVisitor visitor) {
        visitor.visit(this);
    }

    public Boolean getDefaultValue() {
        return defaultValue;
    }
}
