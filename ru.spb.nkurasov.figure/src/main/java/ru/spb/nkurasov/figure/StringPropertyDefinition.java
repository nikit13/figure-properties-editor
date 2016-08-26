package ru.spb.nkurasov.figure;

public class StringPropertyDefinition extends AbstractFigureProperty {

    private final String defaultValue;

    StringPropertyDefinition(String name, String defaultValue) {
        super(name);

        this.defaultValue = defaultValue;
    }

    @Override
    public void accept(FigurePropertyDefinitionVisitor visitor) {
        visitor.visit(this);
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
