package ru.spb.nkurasov.figure;

public class IntegerPropertyDefinition extends AbstractFigureProperty {

    private final Integer defaultValue;

    private final Integer minValue;

    private final Integer maxValue;

    IntegerPropertyDefinition(String name, Integer defaultValue, Integer minValue, Integer maxValue) {
        super(name);

        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public void accept(FigurePropertyDefinitionVisitor visitor) {
        visitor.visit(this);
    }

    public Integer getDefaultValue() {
        return defaultValue;
    }
    
    public Integer getMinValue() {
        return minValue;
    }
    
    public Integer getMaxValue() {
        return maxValue;
    }
}
